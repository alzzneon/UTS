package com.project.uts
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PengunjungDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPengunjung(pengunjung: Pengunjung)

    @Update
    suspend fun updatePengunjung(pengunjung: Pengunjung)

    @Delete
    suspend fun deletePengunjung(pengunjung: Pengunjung)

    @Query("SELECT * FROM pengunjung ORDER BY id ASC")
    fun getAllPengunjung(): List<Pengunjung>

    @Query("SELECT * FROM pengunjung WHERE id = :pengunjungId")
    suspend fun getPengunjungById(pengunjungId: Int): Pengunjung?
}
