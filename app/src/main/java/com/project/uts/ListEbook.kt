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

class ListEbook : AppCompatActivity() {

    private lateinit var bukuDatabase: BukuDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var ebookAdapter: EbookAdapter
    private lateinit var repoEbook: RepoEbook

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_ebook)

        bukuDatabase = BukuDatabase.getDatabase(this)
        recyclerView = findViewById(R.id.rv_listEbook)
        recyclerView.layoutManager = LinearLayoutManager(this)
        repoEbook = RepoEbook(this)

        val addButton = findViewById<FloatingActionButton>(R.id.tambahEbook)

        addButton.setOnClickListener{
            val intent = Intent(this, TambahEbookActivity::class.java)
            startActivity(intent)
        }

        loadEbooks()
    }

    private fun loadEbooks() {
        CoroutineScope(Dispatchers.IO).launch {
            val ebooks = bukuDatabase.ebookDao().getAllEbooks()
            withContext(Dispatchers.Main) {
                ebookAdapter = EbookAdapter(ebooks,
                    onEditClick = { ebook -> showEditDialog(ebook) },
                    onDeleteClick = { ebook -> showDeleteConfirmationDialog(ebook) }
                )
                recyclerView.adapter = ebookAdapter
            }
        }
    }

    private fun showEditDialog(ebook: Ebook) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_editebook, null) // Sesuaikan dengan layout dialog edit ebook

        // Inisialisasi EditText dari layout dialog
        val edtJudul = dialogView.findViewById<EditText>(R.id.edt_juduleb)
        val edtKategori = dialogView.findViewById<EditText>(R.id.edt_kategorieb)
        val edtPenulis = dialogView.findViewById<EditText>(R.id.edt_penuliseb)

        // Set nilai awal untuk ditampilkan dalam dialog
        edtJudul.setText(ebook.namaeb)
        edtKategori.setText(ebook.jeniseb)
        edtPenulis.setText(ebook.penerbiteb)

        AlertDialog.Builder(this)
            .setTitle("Edit Ebook")
            .setView(dialogView)
            .setPositiveButton("Simpan") { dialog, _ ->
                val updatedEbook = Ebook(
                    id = ebook.id,
                    namaeb = edtJudul.text.toString(),
                    jeniseb = edtKategori.text.toString(),
                    penerbiteb = edtPenulis.text.toString()
                )
                updateEbook(updatedEbook)
                dialog.dismiss()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun showDeleteConfirmationDialog(ebook: Ebook) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Ebook")
            .setMessage("Apakah Anda yakin ingin menghapus ebook '${ebook.namaeb}'?")
            .setPositiveButton("Ya") { dialog, _ ->
                deleteEbook(ebook)
                dialog.dismiss()
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun updateEbook(ebook: Ebook) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                bukuDatabase.ebookDao().updateEbook(ebook)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ListEbook, "Ebook berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                    loadEbooks() // Refresh data setelah update
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ListEbook, "Gagal memperbarui ebook!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deleteEbook(ebook: Ebook) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                bukuDatabase.ebookDao().deleteEbook(ebook)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ListEbook, "Ebook berhasil dihapus!", Toast.LENGTH_SHORT).show()
                    loadEbooks() // Refresh data setelah delete
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ListEbook, "Gagal menghapus ebook!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
