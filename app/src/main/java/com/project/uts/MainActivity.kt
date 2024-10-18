package com.project.uts

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.project.uts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)// Menghubungkan dengan layout XML dengan binding

        // Listener untuk tombol "Tambah Buku"
        binding.buttonTambah.setOnClickListener {
            Toast.makeText(this, "Menu Tambah Buku", Toast.LENGTH_SHORT).show()
            // Intent untuk pindah ke activity TambahBuku (belum dibuat)
            // startActivity(Intent(this, TambahBukuActivity::class.java))
        }

        // Listener untuk tombol "Daftar Buku"
        binding.buttonDaftar.setOnClickListener {
            Toast.makeText(this, "Menu Daftar Buku", Toast.LENGTH_SHORT).show()
            // Intent untuk pindah ke activity DaftarBuku (belum dibuat)
            // startActivity(Intent(this, DaftarBukuActivity::class.java))
        }
    }
}
