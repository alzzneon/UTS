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
    private lateinit var repoBuku: RepoBuku
    private lateinit var binding: ActivityDaftarbukuBinding
    private var isEditMode = false
    private var bookId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_daftarbuku)

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
                        Toast.makeText(this@DaftarBuku, message, Toast.LENGTH_SHORT).show()
                        finish() // Kembali ke activity sebelumnya
                    }

                    override fun onError(message: String) {
                        Toast.makeText(this@DaftarBuku, message, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

    }


}
