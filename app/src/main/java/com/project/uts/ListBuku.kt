package com.project.uts

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListBuku : AppCompatActivity() {

    private lateinit var bukuDatabase: BukuDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var bukuAdapter: BukuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_buku)

        bukuDatabase = BukuDatabase.getDatabase(this)
        recyclerView = findViewById(R.id.rv_listBuku)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadBooks()
    }

    private fun loadBooks() {
        CoroutineScope(Dispatchers.IO).launch {
            val books = bukuDatabase.BukuDao().getAllBooks()  // Mengambil semua buku dari database
            withContext(Dispatchers.Main) {
                bukuAdapter = BukuAdapter(books, onEditClick = { buku ->
                    // Handle edit buku
                    editBook(buku)  // Pastikan Anda mengirimkan objek buku
                }, onDeleteClick = { buku ->
                    // Handle hapus buku
                    deleteBook(buku)
                })
                recyclerView.adapter = bukuAdapter
            }
        }
    }

    private fun editBook(buku: Buku) {
        val intent = Intent(this, DaftarBuku::class.java).apply {
            putExtra("EXTRA_ID_BUKU", buku.id)  // Mengirim ID buku
            putExtra("EXTRA_JUDUL_BUKU", buku.judul)  // Mengirim judul buku
            putExtra("EXTRA_KATEGORI_BUKU", buku.kategori)  // Mengirim kategori buku
            putExtra("EXTRA_PENULIS_BUKU", buku.penulis)  // Mengirim penulis buku
        }
        startActivity(intent)  // Memulai activity DaftarBuku
    }

    private fun deleteBook(buku: Buku) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                bukuDatabase.BukuDao().deleteBuku(buku)
                withContext(Dispatchers.Main) {
                    loadBooks() // Refresh data setelah buku dihapus
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    // Menangani error, misalnya dengan menampilkan Toast
                    Toast.makeText(this@ListBuku, "Gagal menghapus buku!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
