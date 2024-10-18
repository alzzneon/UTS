package com.project.uts
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BukuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuku(book: Buku)

    @Update
    suspend fun updateBuku(book: Buku)

    @Delete
    suspend fun deleteBuku(book: Buku)

    @Query("SELECT * FROM buku ORDER BY id ASC")
    fun getAllBooks(): List<Buku>

    @Query("SELECT * FROM buku WHERE id = :bukuId")
    suspend fun getBookById(bukuId: Int): Buku?
}
