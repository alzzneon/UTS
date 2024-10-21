package com.project.uts

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.project.uts.databinding.ActivityDaftarbukuBinding

class DaftarBuku : AppCompatActivity() {

    private lateinit var binding: ActivityDaftarbukuBinding
    private var isEditMode = false
    private var bookId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_daftarbuku)

        binding.btntambahbuku.setOnClickListener {
                addBook()  // Jika mode tambah, tambahkan buku baru
        }
    }

    private fun addBook() {
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

        // Buat objek Buku baru tanpa menyertakan id
        val newBook = Buku(
            judul = bookTitle,
            kategori = bookType,
            penulis = authorPublisher
        )

        // Simpan buku ke database
        CoroutineScope(Dispatchers.IO).launch {
            val db = BukuDatabase.getDatabase(applicationContext)
            db.bukuDao().insertBuku(newBook)  // Panggil fungsi insertBuku
            withContext(Dispatchers.Main) {
                Toast.makeText(this@DaftarBuku, "Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                finish()  // Kembali ke halaman sebelumnya setelah selesai
            }
        }
    }
}
