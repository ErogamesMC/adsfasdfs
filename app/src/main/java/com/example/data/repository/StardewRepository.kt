package com.example.data.repository

import com.example.data.dao.FavoriteDao
import com.example.data.dao.ItemDao
import com.example.data.model.FavoriteEntity
import com.example.data.model.ItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class StardewRepository(
    private val itemDao: ItemDao,
    private val favoriteDao: FavoriteDao
) {
    val allItems: Flow<List<ItemEntity>> = itemDao.getAllItems()
    val allCrops: Flow<List<ItemEntity>> = itemDao.getCrops()
    val allFavorites: Flow<List<FavoriteEntity>> = favoriteDao.getAllFavorites()

    fun searchItems(query: String): Flow<List<ItemEntity>> {
        return if (query.isBlank()) {
            itemDao.getAllItems()
        } else {
            itemDao.searchItems(query)
        }
    }

    fun getItemsByCategory(categoryName: String): Flow<List<ItemEntity>> {
        return itemDao.getItemsByCategory(categoryName)
    }

    fun getItemsBySeason(seasonName: String): Flow<List<ItemEntity>> {
        return itemDao.getItemsBySeason(seasonName)
    }

    fun isFavorite(itemId: String): Flow<Boolean> {
        return favoriteDao.isFavorite(itemId)
    }

    suspend fun toggleFavorite(itemId: String, customNote: String = "") {
        val isFav = favoriteDao.isFavorite(itemId).firstOrNull() ?: false
        if (isFav) {
            favoriteDao.removeFavorite(itemId)
        } else {
            favoriteDao.addFavorite(FavoriteEntity(itemId = itemId, customNote = customNote))
        }
    }

    suspend fun updateFavoriteNote(itemId: String, customNote: String) {
        favoriteDao.addFavorite(FavoriteEntity(itemId = itemId, customNote = customNote))
    }
}
