package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM stardew_items ORDER BY nameEs ASC")
    fun getAllItems(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM stardew_items WHERE isCrop = 1 ORDER BY nameEs ASC")
    fun getCrops(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM stardew_items WHERE category = :category ORDER BY nameEs ASC")
    fun getItemsByCategory(category: String): Flow<List<ItemEntity>>

    @Query("SELECT * FROM stardew_items WHERE season = :season OR season = 'ALL' ORDER BY nameEs ASC")
    fun getItemsBySeason(season: String): Flow<List<ItemEntity>>

    @Query("SELECT * FROM stardew_items WHERE nameEs LIKE '%' || :query || '%' OR nameEn LIKE '%' || :query || '%' OR location LIKE '%' || :query || '%' ORDER BY nameEs ASC")
    fun searchItems(query: String): Flow<List<ItemEntity>>

    @Query("SELECT * FROM stardew_items WHERE id = :id LIMIT 1")
    suspend fun getItemById(id: String): ItemEntity?

    @Query("SELECT COUNT(*) FROM stardew_items")
    suspend fun getItemCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ItemEntity>)
}
