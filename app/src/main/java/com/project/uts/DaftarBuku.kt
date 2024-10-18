package com.project.uts

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.project.uts.databinding.ActivityDaftarbukuBinding

class DaftarBuku : AppCompatActivity() {

    private lateinit var binding: ActivityDaftarbukuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_daftarbuku)
        binding.btntambahbuku.setOnClickListener {
            addBook()
        }
    }

    private fun addBook() {
        val bookId = binding.etidbuku.text.toString()
        val bookTitle = binding.etjudulbuku.text.toString()
        val bookType = binding.etkategori.text.toString()
        val authorPublisher = binding.etpenerbit.text.toString()

        if (TextUtils.isEmpty(bookId)) {
            binding.etidbuku.error = "ID Buku diperlukan"
            return
        }
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

        Toast.makeText(this, "Buku Ditambahkan:\n" +
                "ID: $bookId\n" +
                "Judul: $bookTitle\n" +
                "Kategori: $bookType\n" +
                "Penerbit/Penulis: $authorPublisher", Toast.LENGTH_LONG).show()

        binding.etidbuku.text.clear()
        binding.etjudulbuku.text.clear()
        binding.etkategori.text.clear()
        binding.etpenerbit.text.clear()
    }
}
