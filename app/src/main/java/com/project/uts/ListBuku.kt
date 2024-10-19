package com.project.uts

import android.os.Bundle
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
                    // Logika edit buku
                    editBook(buku)
                }, onDeleteClick = { buku ->
                    // Logika hapus buku
                    deleteBook(buku)
                })
                recyclerView.adapter = bukuAdapter
            }
        }
    }

    private fun editBook(buku: Buku) {
        // Implementasi fitur edit buku
    }

    private fun deleteBook(buku: Buku) {
        CoroutineScope(Dispatchers.IO).launch {
            bukuDatabase.BukuDao().deleteBuku(buku)
            loadBooks() // Refresh data setelah buku dihapus
        }
    }
}
