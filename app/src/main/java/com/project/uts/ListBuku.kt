package com.project.uts

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListBuku : AppCompatActivity() {

    private lateinit var bukuDatabase: BukuDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var bukuAdapter: BukuAdapter
    private lateinit var repoBuku: RepoBuku

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_buku)

        bukuDatabase = BukuDatabase.getDatabase(this)
        recyclerView = findViewById(R.id.rv_listBuku)
        recyclerView.layoutManager = LinearLayoutManager(this)
        repoBuku = RepoBuku(this)

        val addButton = findViewById<FloatingActionButton>(R.id.tambahBuku)

        addButton.setOnClickListener{
            val intent = Intent(this, DaftarBuku::class.java)
            startActivity(intent)
        }

        loadBooks()
    }

    private fun loadBooks() {
        CoroutineScope(Dispatchers.IO).launch {
            val books = bukuDatabase.bukuDao().getAllBooks()
            withContext(Dispatchers.Main) {
                bukuAdapter = BukuAdapter(books,
                    onEditClick = { buku -> showEditDialog(buku) },
                    onDeleteClick = { buku -> showDeleteConfirmationDialog(buku) }
                )
                recyclerView.adapter = bukuAdapter
            }
        }
    }

    private fun showEditDialog(buku: Buku) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit, null)

        // Inisialisasi EditText dari layout dialog
        val edtJudul = dialogView.findViewById<EditText>(R.id.edt_judul)
        val edtKategori = dialogView.findViewById<EditText>(R.id.edt_kategori)
        val edtPenulis = dialogView.findViewById<EditText>(R.id.edt_penulis)

        // Set nilai awal
        edtJudul.setText(buku.judul)
        edtKategori.setText(buku.kategori)
        edtPenulis.setText(buku.penulis)

        AlertDialog.Builder(this)
            .setTitle("Edit Buku")
            .setView(dialogView)
            .setPositiveButton("Simpan") { dialog, _ ->
                val updatedBuku = Buku(
                    id = buku.id,
                    judul = edtJudul.text.toString(),
                    kategori = edtKategori.text.toString(),
                    penulis = edtPenulis.text.toString()
                )
                updateBook(updatedBuku)
                dialog.dismiss()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun showDeleteConfirmationDialog(buku: Buku) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Buku")
            .setMessage("Apakah Anda yakin ingin menghapus buku '${buku.judul}'?")
            .setPositiveButton("Ya") { dialog, _ ->
                deleteBook(buku)
                dialog.dismiss()
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun updateBook(buku: Buku) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                bukuDatabase.bukuDao().updateBuku(buku)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ListBuku, "Buku berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                    loadBooks() // Refresh data setelah update
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ListBuku, "Gagal memperbarui buku!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deleteBook(buku: Buku) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                bukuDatabase.bukuDao().deleteBuku(buku)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ListBuku, "Buku berhasil dihapus!", Toast.LENGTH_SHORT).show()
                    loadBooks() // Refresh data setelah delete
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ListBuku, "Gagal menghapus buku!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}