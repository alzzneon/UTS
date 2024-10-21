package com.project.uts

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.project.uts.databinding.ActivityUpdateBukuBinding

class UpdateBuku : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBukuBinding
    private var isEditMode = false
    private var bookId: Int? = null  // Variabel untuk menyimpan ID buku

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_buku)

        // Cek apakah activity ini dibuka dalam mode edit
        if (intent.hasExtra("EXTRA_ID_BUKU")) {
            isEditMode = true
            // Mendapatkan data buku yang akan diedit
            bookId = intent.getIntExtra("EXTRA_ID_BUKU", 0)
            val judulBuku = intent.getStringExtra("EXTRA_JUDUL_BUKU")
            val kategoriBuku = intent.getStringExtra("EXTRA_KATEGORI_BUKU")
            val penulisBuku = intent.getStringExtra("EXTRA_PENULIS_BUKU")

            // Menampilkan data pada form
            binding.etjudulbuku.setText(judulBuku)
            binding.etkategori.setText(kategoriBuku)
            binding.etpenerbit.setText(penulisBuku)
        }

        binding.btnUbahBuku.setOnClickListener {
            if (isEditMode) {
                updateBook()  // Jika mode edit, panggil fungsi update
            }
        }
    }

    private fun updateBook() {
        val bookTitle = binding.etjudulbuku.text.toString()
        val bookType = binding.etkategori.text.toString()
        val authorPublisher = binding.etpenerbit.text.toString()

        // Validasi input
        if (TextUtils.isEmpty(bookTitle)) {
            binding.etjudulbuku.error = "Judul Buku diperlukan"
            return
        }
        if (TextUtils.isEmpty(bookType)) {
            binding.etkategori.error = "Kategori Buku diperlukan"
            return
        }
        if (TextUtils.isEmpty(authorPublisher)) {
            binding.etpenerbit.error = "Penerbit/Penulis diperlukan"
            return
        }

        // Buat objek buku baru dengan ID dan data yang diperbarui
        val updatedBook = Buku(
            id = bookId ?: 0,  // Pastikan ID buku tidak null
            judul = bookTitle,
            kategori = bookType,
            penulis = authorPublisher
        )

        // Lakukan update di background thread menggunakan coroutine
        CoroutineScope(Dispatchers.IO).launch {
            val db = BukuDatabase.getDatabase(applicationContext)
            db.bukuDao().updateBuku(updatedBook)  // Panggil fungsi update

            withContext(Dispatchers.Main) {
                Toast.makeText(this@UpdateBuku, "Buku berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                finish()  // Kembali ke halaman sebelumnya setelah selesai update
            }
        }
    }
}
