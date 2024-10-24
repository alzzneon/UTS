package com.project.uts

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.project.uts.databinding.ActivityMainBinding
import android.content.Intent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Listener untuk tombol "E Buku"
        binding.buttonebook.setOnClickListener {
            Toast.makeText(this, "Menu List Buku", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,ListEbook ::class.java)
            startActivity(intent)
        }

        // Listener untuk tombol "Buku"
        binding.buttonDaftar.setOnClickListener {
            Toast.makeText(this, "Menu List Ebook", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ListBuku::class.java)
            startActivity(intent)
        }
        binding.buttonTambahPengunjung.setOnClickListener {
            Toast.makeText(this, "Menu Daftar Buku", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TambahPengujungActivity::class.java)
            startActivity(intent)
        }
    }
}

