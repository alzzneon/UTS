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

        // Cek apakah activity ini dibuka dalam mode edit
        if (intent.hasExtra("EXTRA_ID_BUKU")) {
            isEditMode = true
            // Mendapatkan data buku yang akan diedit
            bookId = intent.getIntExtra("EXTRA_ID_BUKU", 0)
            val judulBuku = intent.getStringExtra("EXTRA_JUDUL_BUKU")
            val kategoriBuku = intent.getStringExtra("EXTRA_KATEGORI_BUKU")
            val penulisBuku = intent.getStringExtra("EXTRA_PENULIS_BUKU")

            // Menampilkan data pada form
            binding.etidbuku.setText(bookId.toString())
            binding.etjudulbuku.setText(judulBuku)
            binding.etkategori.setText(kategoriBuku)
            binding.etpenerbit.setText(penulisBuku)

            binding.btntambahbuku.text = "Update Buku"  // Ubah teks tombol menjadi "Update"
        }

        binding.btntambahbuku.setOnClickListener {
            if (isEditMode) {
                updateBook()  // Jika mode edit, maka update buku
            } else {
                addBook()  // Jika mode tambah, tambahkan buku baru
            }
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
            db.BukuDao().insertBuku(newBook)  // Panggil fungsi insertBuku
            withContext(Dispatchers.Main) {
                Toast.makeText(this@DaftarBuku, "Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                finish()  // Kembali ke halaman sebelumnya setelah selesai
            }
        }
    }


    private fun updateBook() {
        val bookId = binding.etidbuku.text.toString().toIntOrNull() // Ambil ID dari EditText
        val bookTitle = binding.etjudulbuku.text.toString()
        val bookType = binding.etkategori.text.toString()
        val authorPublisher = binding.etpenerbit.text.toString()

        // Validasi input
        if (bookId == null) {
            binding.etidbuku.error = "ID Buku tidak valid"
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

        // Buat objek buku baru dengan data yang diperbarui
        val updatedBook = Buku(
            id = bookId,  // Pastikan untuk menggunakan ID yang ada
            judul = bookTitle,
            kategori = bookType,
            penulis = authorPublisher
        )

        // Simpan perubahan ke database
        CoroutineScope(Dispatchers.IO).launch {
            val db = BukuDatabase.getDatabase(applicationContext)
            db.BukuDao().updateBuku(updatedBook) // Panggil fungsi updateBuku
            withContext(Dispatchers.Main) {
                Toast.makeText(this@DaftarBuku, "Buku berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                finish()  // Kembali ke halaman sebelumnya setelah selesai update
            }
        }
    }
}
