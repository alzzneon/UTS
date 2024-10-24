package com.project.uts

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Buku::class, Ebook::class, Pengunjung::class], version = 1, exportSchema = false)
abstract class BukuDatabase : RoomDatabase() {
    abstract fun bukuDao(): BukuDao
    abstract fun pengunjungDao(): PengunjungDao
    abstract fun ebookDao(): EbookDao

    companion object {
        @Volatile
        private var INSTANCE: BukuDatabase? = null

        fun getDatabase(context: Context): BukuDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BukuDatabase::class.java,
                    "buku_ebook_pengunjung_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
