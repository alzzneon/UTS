package com.project.uts

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.project.uts.databinding.ActivityTambahebookBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TambahEbookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahebookBinding
    private lateinit var repoEbook: RepoEbook

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityTambahebookBinding.inflate(layoutInflater)
        setContentView(binding.root)  // Use the root view from binding

        // Initialize RepoEbook
        repoEbook = RepoEbook(this)

        // Set onClickListener for the add button
        binding.ebookTambahButton.setOnClickListener {
            val nama = binding.ebookNamaEditText.text.toString().trim()
            val penerbit = binding.ebookPenerbitEditText.text.toString().trim()
            val jenis = binding.ebookJenisEditText.text.toString().trim()

            // Call addEbook function
            repoEbook.addEbook(nama, penerbit, jenis,
                binding = binding,
                callback = object : RepoEbook.EbookCallback {
                    override fun onSuccess(message: String) {
                        Toast.makeText(this@TambahEbookActivity, message, Toast.LENGTH_SHORT).show()
                        // Optionally, clear the input fields after success
                        binding.ebookNamaEditText.text.clear()
                        binding.ebookPenerbitEditText.text.clear()
                        binding.ebookJenisEditText.text.clear()
                    }

                    override fun onError(message: String) {
                        Toast.makeText(this@TambahEbookActivity, message, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}
