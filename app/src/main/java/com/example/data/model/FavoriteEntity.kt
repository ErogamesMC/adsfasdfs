package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_items")
data class FavoriteEntity(
    @PrimaryKey val itemId: String,
    val addedTimestamp: Long = System.currentTimeMillis(),
    val customNote: String = ""
)
