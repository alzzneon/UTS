package com.project.uts

import androidx.room.*

@Dao
interface EbookDao {

    @Insert
    suspend fun insertEbook(ebook: Ebook)

    @Update
    suspend fun updateEbook(ebook: Ebook)

    @Delete
    suspend fun deleteEbook(ebook: Ebook)

    @Query("SELECT * FROM ebook")
    suspend fun getAllEbooks(): List<Ebook>

    @Query("SELECT * FROM ebook WHERE id = :ebookId LIMIT 1")
    suspend fun getEbookById(ebookId: Int): Ebook?
}
