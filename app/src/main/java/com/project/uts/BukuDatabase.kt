package com.project.uts

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [Buku::class], version = 1, exportSchema = false)
abstract class BukuDatabase : RoomDatabase() {

    abstract fun BukuDao(): BukuDao

    companion object {
        @Volatile
        private var INSTANCE: BukuDatabase? = null

        fun getDatabase(context: Context): BukuDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BukuDatabase::class.java,
                    "buku_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
