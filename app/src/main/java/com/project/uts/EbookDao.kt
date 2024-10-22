package com.project.uts

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EbookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEbook(ebook: Ebook)

    @Update
    suspend fun updateEbook(ebook: Ebook)

    @Delete
    suspend fun deleteEbook(ebook: Ebook)

    @Query("SELECT * FROM ebook ORDER BY id ASC")
    suspend fun getAllEbooks(): List<Ebook>

    @Query("SELECT * FROM ebook WHERE id = :ebookId")
    suspend fun getEbookById(ebookId: Int): Ebook?

    @Query("SELECT * FROM ebook WHERE kategori = :kategori ORDER BY id ASC")
    suspend fun getEbooksByKategori(kategori: String): List<Ebook>
}
