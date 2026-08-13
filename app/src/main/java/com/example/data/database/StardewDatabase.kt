package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.dao.FavoriteDao
import com.example.data.dao.ItemDao
import com.example.data.model.FavoriteEntity
import com.example.data.model.ItemEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ItemEntity::class, FavoriteEntity::class], version = 1, exportSchema = false)
abstract class StardewDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: StardewDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): StardewDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StardewDatabase::class.java,
                    "stardew_guide_db"
                )
                    .addCallback(StardewDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class StardewDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.itemDao())
                    }
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // Ensure all items are inserted/updated with latest preloaded data
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.itemDao())
                    }
                }
            }

            suspend fun populateDatabase(itemDao: ItemDao) {
                itemDao.insertAll(PreloadedData.items)
            }
        }
    }
}
