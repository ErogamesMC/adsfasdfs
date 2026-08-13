package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stardew_items")
data class ItemEntity(
    @PrimaryKey val id: String,
    val nameEs: String,
    val nameEn: String,
    val category: String, // ItemCategory
    val season: String,   // Season
    val location: String, // Ubicación
    val source: String,   // Forma de Obtención
    val sellPriceNormal: Int,
    val sellPriceSilver: Int = (sellPriceNormal * 1.25).toInt(),
    val sellPriceGold: Int = (sellPriceNormal * 1.5).toInt(),
    val sellPriceIridium: Int = sellPriceNormal * 2,
    val seedPrice: Int = 0,
    val growthDays: Int = 0,
    val regrowthDays: Int = 0,
    val multiYieldAvg: Float = 1.0f,
    val jarPrice: Int = 0,
    val kegPrice: Int = 0,
    val description: String = "",
    val isCrop: Boolean = false
)
