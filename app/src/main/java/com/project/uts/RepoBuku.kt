package com.project.uts

import android.content.Context
import android.text.TextUtils
import com.project.uts.databinding.ActivityTambahbukuBinding
import com.project.uts.databinding.ActivityUpdateBukuBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepoBuku(private val context: Context) {
    private val bukuDatabase: BukuDatabase = BukuDatabase.getDatabase(context)

    // Interface untuk callback
    interface BukuCallback {
        fun onSuccess(message: String)
        fun onError(message: String)
    }

    // Fungsi validasi input
    private fun validateInput(
        bookTitle: String,
        bookType: String,
        authorPublisher: String,
        binding: Any
    ): Boolean {
        var isValid = true

        when(binding) {
            is ActivityTambahbukuBinding-> {
                if (TextUtils.isEmpty(bookTitle)) {
                    binding.etjudulbuku.error = "Judul Buku diperlukan"
                    isValid = false
                }
                if (TextUtils.isEmpty(bookType)) {
                    binding.etkategori.error = "Kategori Buku diperlukan"
                    isValid = false
                }
                if (TextUtils.isEmpty(authorPublisher)) {
                    binding.etpenerbit.error = "Penerbit/Penulis diperlukan"
                    isValid = false
                }
            }
            is ActivityUpdateBukuBinding -> {
                // Validasi serupa untuk update binding
                if (TextUtils.isEmpty(bookTitle)) {
                    binding.etjudulbuku.error = "Judul Buku diperlukan"
                    isValid = false
                }
                if (TextUtils.isEmpty(bookType)) {
                    binding.etkategori.error = "Kategori Buku diperlukan"
                    isValid = false
                }
                if (TextUtils.isEmpty(authorPublisher)) {
                    binding.etpenerbit.error = "Penerbit/Penulis diperlukan"
                    isValid = false
                }
            }
        }
        return isValid
    }

    // Fungsi tambah buku
    fun addBook(
        bookTitle: String,
        bookType: String,
        authorPublisher: String,
        binding: ActivityTambahbukuBinding,
        callback: BukuCallback
    ) {
        if (!validateInput(bookTitle, bookType, authorPublisher, binding)) return

        val newBook = Buku(
            judul = bookTitle,
            kategori = bookType,
            penulis = authorPublisher
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                bukuDatabase.bukuDao().insertBuku(newBook)
                withContext(Dispatchers.Main) {
                    callback.onSuccess("Buku berhasil ditambahkan!")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback.onError("Gagal menambahkan buku!")
                }
            }
        }
    }

    // Fungsi update buku
    fun updateBook(
        bookId: Int,
        bookTitle: String,
        bookType: String,
        authorPublisher: String,
        binding: ActivityUpdateBukuBinding,
        callback: BukuCallback
    ) {
        if (!validateInput(bookTitle, bookType, authorPublisher, binding)) return

        val updatedBook = Buku(
            id = bookId,
            judul = bookTitle,
            kategori = bookType,
            penulis = authorPublisher
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                bukuDatabase.bukuDao().updateBuku(updatedBook)
                withContext(Dispatchers.Main) {
                    callback.onSuccess("Buku berhasil diperbarui!")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback.onError("Gagal memperbarui buku!")
                }
            }
        }
    }

    // Fungsi hapus buku
    fun deleteBook(buku: Buku, callback: BukuCallback) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                bukuDatabase.bukuDao().deleteBuku(buku)
                withContext(Dispatchers.Main) {
                    callback.onSuccess("Buku berhasil dihapus!")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback.onError("Gagal menghapus buku!")
                }
            }
        }
    }
}