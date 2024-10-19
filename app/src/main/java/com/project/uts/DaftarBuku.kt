package com.project.uts

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.project.uts.databinding.ActivityDaftarbukuBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DaftarBuku : AppCompatActivity() {

    private lateinit var binding: ActivityDaftarbukuBinding
    private lateinit var bukuDatabase: BukuDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_daftarbuku)
        bukuDatabase = BukuDatabase.getDatabase(this)  // Inisialisasi database

        binding.btntambahbuku.setOnClickListener {
            addBook()
        }
    }

    private fun addBook() {
        val bookTitle = binding.etjudulbuku.text.toString()
        val bookType = binding.etkategori.text.toString()
        val authorPublisher = binding.etpenerbit.text.toString()

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

        // Buat objek Buku
        val newBook = Buku(
            judul = bookTitle,
            buku = bookTitle,  // Jika 'buku' seharusnya sama dengan judul
            penulis = authorPublisher,
            kategori = bookType
        )

        // Gunakan lifecycleScope untuk operasi database
        lifecycleScope.launch(Dispatchers.IO) {
            val bukuDatabase = BukuDatabase.getDatabase(this@DaftarBuku)
            bukuDatabase.BukuDao().insertBuku(newBook)
        }

        Toast.makeText(
            this, "Buku Ditambahkan:\n" +
                    "Judul: $bookTitle\n" +
                    "Kategori: $bookType\n" +
                    "Penerbit/Penulis: $authorPublisher", Toast.LENGTH_LONG
        ).show()

        // Bersihkan inputan setelah buku ditambahkan
        binding.etjudulbuku.text.clear()
        binding.etkategori.text.clear()
        binding.etpenerbit.text.clear()
    }
}
