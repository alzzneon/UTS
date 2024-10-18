package com.project.uts

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class daftarbuku : AppCompatActivity() {

    // Deklarasi komponen UI
    private lateinit var etBookId: EditText
    private lateinit var etBookTitle: EditText
    private lateinit var etBookType: EditText
    private lateinit var etAuthorPublisher: EditText
    private lateinit var btnAddBook: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Mengaktifkan fitur Edge-to-Edge jika diperlukan
        setContentView(R.layout.activity_daftarbuku)

        etBookId = findViewById(R.id.etidbuku)
        etBookTitle = findViewById(R.id.etjudulbuku)
        etBookType = findViewById(R.id.etkategori)
        etAuthorPublisher = findViewById(R.id.etpenerbit)
        btnAddBook = findViewById(R.id.btntambahbuku)

        btnAddBook.setOnClickListener {
            addBook()
        }
    }

    private fun addBook() {
        val bookId = etBookId.text.toString()
        val bookTitle = etBookTitle.text.toString()
        val bookType = etBookType.text.toString()
        val authorPublisher = etAuthorPublisher.text.toString()

        if (TextUtils.isEmpty(bookId)) {
            etBookId.error = "ID Buku diperlukan"
            return
        }
        if (TextUtils.isEmpty(bookTitle)) {
            etBookTitle.error = "Judul Buku diperlukan"
            return
        }
        if (TextUtils.isEmpty(bookType)) {
            etBookType.error = "Kategori Buku diperlukan"
            return
        }
        if (TextUtils.isEmpty(authorPublisher)) {
            etAuthorPublisher.error = "Penerbit/Penulis diperlukan"
            return
        }

        Toast.makeText(this, "Buku Ditambahkan:\n" +
                "ID: $bookId\n" +
                "Judul: $bookTitle\n" +
                "Kategori: $bookType\n" +
                "Penerbit/Penulis: $authorPublisher", Toast.LENGTH_LONG).show()

        etBookId.text.clear()
        etBookTitle.text.clear()
        etBookType.text.clear()
        etAuthorPublisher.text.clear()
    }
}
