package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.database.PreloadedData
import com.example.data.model.ItemCategory
import com.example.data.model.ItemEntity
import com.example.data.model.Season
import com.example.data.model.SeasonEvent
import com.example.data.repository.StardewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class SortOrder(val displayName: String) {
    PRICE_DESC("💰 Valor (Mejor a Peor)"),
    PRICE_ASC("🏷️ Valor (Menor a Mayor)"),
    NAME_ASC("🔤 Nombre (A - Z)"),
    GROWTH_ASC("🌱 Crecimiento (Rápido)")
}

data class CalculatorResult(
    val cropName: String,
    val totalSeedsCost: Int,
    val grossRevenue: Int,
    val netProfit: Int,
    val profitPerDay: Float,
    val totalHarvestsInSeason: Int,
    val daysToFirstHarvest: Int,
    val unitSalePrice: Int,
    val processingLabel: String
)

class StardewViewModel(private val repository: StardewRepository) : ViewModel() {

    // --- Search & Filters ---
    val searchQuery = MutableStateFlow("")
    val selectedCategory = MutableStateFlow<ItemCategory?>(null)
    val selectedSeason = MutableStateFlow<Season?>(null)
    val sortOrder = MutableStateFlow(SortOrder.PRICE_DESC)

    // Favorites
    val favoriteIds: StateFlow<Set<String>> = repository.allFavorites
        .map { favs -> favs.map { it.itemId }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    // All Items with dynamic favorite flag and sorting (default Best to Worst by sell price)
    val filteredItems: StateFlow<List<ItemEntity>> = combine(
        repository.allItems,
        searchQuery,
        selectedCategory,
        selectedSeason,
        sortOrder,
        favoriteIds
    ) { flows: Array<Any?> ->
        @Suppress("UNCHECKED_CAST")
        val items = flows[0] as List<ItemEntity>
        val query = flows[1] as String
        val category = flows[2] as? ItemCategory
        val season = flows[3] as? Season
        val sort = flows[4] as SortOrder

        val filtered = items.filter { item ->
            val matchesQuery = query.isBlank() ||
                    item.nameEs.contains(query, ignoreCase = true) ||
                    item.nameEn.contains(query, ignoreCase = true) ||
                    item.location.contains(query, ignoreCase = true) ||
                    item.source.contains(query, ignoreCase = true) ||
                    item.category.contains(query, ignoreCase = true)

            val matchesCategory = category == null || item.category == category.name
            val matchesSeason = season == null || item.season == season.name || item.season == Season.ALL.name

            matchesQuery && matchesCategory && matchesSeason
        }

        when (sort) {
            SortOrder.PRICE_DESC -> filtered.sortedByDescending { it.sellPriceNormal }
            SortOrder.PRICE_ASC -> filtered.sortedBy { it.sellPriceNormal }
            SortOrder.NAME_ASC -> filtered.sortedBy { it.nameEs }
            SortOrder.GROWTH_ASC -> filtered.sortedBy { if (it.growthDays > 0) it.growthDays else Int.MAX_VALUE }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Favorite Items List
    val favoriteItems: StateFlow<List<ItemEntity>> = combine(
        repository.allItems,
        favoriteIds
    ) { items, favSet ->
        items.filter { favSet.contains(it.id) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // All Crops List for Calculator selector
    val cropsList: StateFlow<List<ItemEntity>> = repository.allCrops
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- Calculator State ---
    val calcSelectedCrop = MutableStateFlow<ItemEntity?>(null)
    val calcQuantity = MutableStateFlow(10)
    val calcTillerProfession = MutableStateFlow(false) // +10% crop sale price
    val calcArtisanProfession = MutableStateFlow(false) // +40% artisan price
    val calcQuality = MutableStateFlow("NORMAL") // NORMAL, SILVER, GOLD, IRIDIUM
    val calcProcessingType = MutableStateFlow("RAW") // RAW, JAR, KEG
    val calcFertilizer = MutableStateFlow("NONE") // NONE, SPEED_GRO (10% faster), DELUXE_SPEED_GRO (25% faster)

    // Calculation result state
    val calculatorResult: StateFlow<CalculatorResult?> = combine(
        calcSelectedCrop,
        calcQuantity,
        calcTillerProfession,
        calcArtisanProfession,
        calcQuality,
        calcProcessingType,
        calcFertilizer
    ) { args: Array<Any?> ->
        val crop = args[0] as? ItemEntity
        val quantity = args[1] as Int
        val tiller = args[2] as Boolean
        val artisan = args[3] as Boolean
        val quality = args[4] as String
        val processing = args[5] as String
        val fertilizer = args[6] as String

        if (crop == null) return@combine null

        // Growth speed reduction
        val speedFactor = when (fertilizer) {
            "SPEED_GRO" -> 0.10f
            "DELUXE_SPEED_GRO" -> 0.25f
            else -> 0.0f
        }

        val baseGrowth = crop.growthDays
        val reducedGrowth = maxOf(1, (baseGrowth * (1.0f - speedFactor)).toInt())

        // Calculate harvests in 28 days
        val harvests = if (crop.regrowthDays > 0) {
            if (28 >= reducedGrowth) {
                1 + (28 - reducedGrowth) / crop.regrowthDays
            } else 0
        } else {
            28 / reducedGrowth
        }

        // Calculate unit sale price
        var unitPrice = when (quality) {
            "SILVER" -> crop.sellPriceSilver
            "GOLD" -> crop.sellPriceGold
            "IRIDIUM" -> crop.sellPriceIridium
            else -> crop.sellPriceNormal
        }

        var processingLabel = "Sin procesar (Crudo)"

        if (processing == "JAR" && crop.jarPrice > 0) {
            unitPrice = crop.jarPrice
            processingLabel = "Jarra de Mermelada / Encurtido"
            if (artisan) {
                unitPrice = (unitPrice * 1.4f).toInt()
            }
        } else if (processing == "KEG" && crop.kegPrice > 0) {
            unitPrice = crop.kegPrice
            processingLabel = "Barril (Vino / Cerveza / Jugo)"
            if (artisan) {
                unitPrice = (unitPrice * 1.4f).toInt()
            }
        } else {
            if (tiller) {
                unitPrice = (unitPrice * 1.1f).toInt()
            }
        }

        // Seeds cost (if re-harvestable crop, you only buy seeds ONCE per season!)
        val seedPurchases = if (crop.regrowthDays > 0) quantity else (quantity * harvests)
        val totalSeedCost = crop.seedPrice * seedPurchases

        val totalUnitsHarvested = (quantity * harvests * crop.multiYieldAvg).toInt()
        val grossRevenue = unitPrice * totalUnitsHarvested
        val netProfit = grossRevenue - totalSeedCost

        val profitPerDay = if (harvests > 0) {
            netProfit.toFloat() / 28.0f
        } else 0.0f

        CalculatorResult(
            cropName = crop.nameEs,
            totalSeedsCost = totalSeedCost,
            grossRevenue = grossRevenue,
            netProfit = netProfit,
            profitPerDay = profitPerDay,
            totalHarvestsInSeason = harvests,
            daysToFirstHarvest = reducedGrowth,
            unitSalePrice = unitPrice,
            processingLabel = processingLabel
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // --- Calendar State ---
    val calendarSeason = MutableStateFlow(Season.SPRING)
    val selectedCalendarDay = MutableStateFlow<Int?>(null)

    val currentSeasonEvents: StateFlow<List<SeasonEvent>> = calendarSeason.map { season ->
        PreloadedData.calendarEvents.filter { it.season == season }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        // Default select Fresa or Carambola for calculator on start
        viewModelScope.launch {
            repository.allCrops.collect { crops ->
                if (calcSelectedCrop.value == null && crops.isNotEmpty()) {
                    calcSelectedCrop.value = crops.firstOrNull { it.id == "crop_fresa" } ?: crops.first()
                }
            }
        }
    }

    // --- Actions ---
    fun toggleFavorite(itemId: String) {
        viewModelScope.launch {
            repository.toggleFavorite(itemId)
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    fun onCategorySelected(category: ItemCategory?) {
        selectedCategory.value = if (selectedCategory.value == category) null else category
    }

    fun onSeasonSelected(season: Season?) {
        selectedSeason.value = if (selectedSeason.value == season) null else season
    }

    fun onSortOrderSelected(order: SortOrder) {
        sortOrder.value = order
    }

    fun selectCropForCalculator(crop: ItemEntity) {
        calcSelectedCrop.value = crop
    }
}

class StardewViewModelFactory(private val repository: StardewRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StardewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StardewViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
