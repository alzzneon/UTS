package com.project.uts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ebook")
data class Ebook(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val namaeb: String,
    val penerbiteb: String,
    val jeniseb: String
)
