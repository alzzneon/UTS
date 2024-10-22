package com.project.uts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ebook")
data class Ebook(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val judul: String,
    val penulis: String,
    val kategori: String
)
