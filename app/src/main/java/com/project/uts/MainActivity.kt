package com.project.uts

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Menghubungkan dengan layout XML

        // Inisialisasi tombol dari layout
        val buttonTambah = findViewById<Button>(R.id.buttonTambah)
        val buttonDaftar = findViewById<Button>(R.id.buttonDaftar)

        // Listener untuk tombol "Tambah Buku"
        buttonTambah.setOnClickListener {
            Toast.makeText(this, "Menu Tambah Buku", Toast.LENGTH_SHORT).show()
            // Intent untuk pindah ke activity TambahBuku (belum dibuat)
            // startActivity(Intent(this, TambahBukuActivity::class.java))
        }

        // Listener untuk tombol "Daftar Buku"
        buttonDaftar.setOnClickListener {
            Toast.makeText(this, "Menu Daftar Buku", Toast.LENGTH_SHORT).show()
            // Intent untuk pindah ke activity DaftarBuku (belum dibuat)
            // startActivity(Intent(this, DaftarBukuActivity::class.java))
        }
    }
}
