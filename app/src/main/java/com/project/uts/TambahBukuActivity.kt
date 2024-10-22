package com.project.uts

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.project.uts.databinding.ActivityTambahbukuBinding

class TambahBukuActivity : AppCompatActivity() {
    private lateinit var repoBuku: RepoBuku
    private lateinit var binding: ActivityTambahbukuBinding
    private var isEditMode = false
    private var bookId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tambahbuku)

        repoBuku = RepoBuku(this)

        binding.btntambahbuku.setOnClickListener {
            val bookTitle = binding.etjudulbuku.text.toString()
            val bookType = binding.etkategori.text.toString()
            val authorPublisher = binding.etpenerbit.text.toString()

            // Panggil addBook dan berikan callback
            repoBuku.addBook(
                bookTitle,
                bookType,
                authorPublisher,
                binding,
                object : RepoBuku.BukuCallback {
                    override fun onSuccess(message: String) {
                        Toast.makeText(this@TambahBukuActivity, message, Toast.LENGTH_SHORT).show()
                        finish() // Kembali ke activity sebelumnya
                    }

                    override fun onError(message: String) {
                        Toast.makeText(this@TambahBukuActivity, message, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

    }


}
