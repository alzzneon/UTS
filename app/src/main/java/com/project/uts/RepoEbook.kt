package com.project.uts

import android.content.Context
import android.text.TextUtils
import com.project.uts.databinding.ActivityTambahebookBinding
import com.project.uts.databinding.ActivityUpdateEbookBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepoEbook(private val context: Context) {
    private val ebookDatabase: BukuDatabase = BukuDatabase.getDatabase(context)

    // Interface untuk callback
    interface EbookCallback {
        fun onSuccess(message: String)
        fun onError(message: String)
    }

    // Fungsi validasi input
    private fun validateInput(
        ebookName: String,
        ebookPublisher: String,
        ebookType: String,
        binding: Any
    ): Boolean {
        var isValid = true

        when (binding) {
            is ActivityTambahebookBinding -> {
                if (TextUtils.isEmpty(ebookName)) {
                    binding.ebookNamaEditText.error = "Nama Ebook diperlukan"
                    isValid = false
                }
                if (TextUtils.isEmpty(ebookPublisher)) {
                    binding.ebookPenerbitEditText.error = "Penerbit diperlukan"
                    isValid = false
                }
                if (TextUtils.isEmpty(ebookType)) {
                    binding.ebookJenisEditText.error = "Jenis Ebook diperlukan"
                    isValid = false
                }
            }
            is ActivityUpdateEbookBinding -> {
                // Validasi serupa untuk update binding
                if (TextUtils.isEmpty(ebookName)) {
                    binding.etJudulEbook.error = "Nama Ebook diperlukan"
                    isValid = false
                }
                if (TextUtils.isEmpty(ebookPublisher)) {
                    binding.etPenerbitEbook.error = "Penerbit diperlukan"
                    isValid = false
                }
                if (TextUtils.isEmpty(ebookType)) {
                    binding.etKategoriEbook.error = "Jenis Ebook diperlukan"
                    isValid = false
                }
            }
        }
        return isValid
    }

    fun addEbook (
        ebookName: String,
        ebookPublisher: String,
        ebookType: String,
        binding: ActivityTambahebookBinding,
        callback: EbookCallback
    ) {
        if (!validateInput(ebookName, ebookPublisher, ebookType, binding)) return

        val newEbook = Ebook(
            namaeb = ebookName,
            penerbiteb = ebookPublisher,
            jeniseb = ebookType
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                ebookDatabase.ebookDao().insertEbook(newEbook) // Perbaikan di sini
                withContext(Dispatchers.Main) {
                    callback.onSuccess("Ebook berhasil ditambahkan!")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback.onError("Gagal menambahkan ebook!")
                }
            }
        }
    }

    // Fungsi update ebook
    fun updateEbook(
        ebookId: Int,
        ebookName: String,
        ebookPublisher: String,
        ebookType: String,
        binding: ActivityUpdateEbookBinding,
        callback: EbookCallback
    ) {
        if (!validateInput(ebookName, ebookPublisher, ebookType, binding)) return

        val updatedEbook = Ebook(
            id = ebookId,
            namaeb = ebookName,
            penerbiteb = ebookPublisher,
            jeniseb = ebookType
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                ebookDatabase.ebookDao().updateEbook(updatedEbook)
                withContext(Dispatchers.Main) {
                    callback.onSuccess("Ebook berhasil diperbarui!")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback.onError("Gagal memperbarui ebook!")
                }
            }
        }
    }

    // Fungsi hapus ebook
    fun deleteEbook(ebook: Ebook, callback: EbookCallback) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                ebookDatabase.ebookDao().deleteEbook(ebook)
                withContext(Dispatchers.Main) {
                    callback.onSuccess("Ebook berhasil dihapus!")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback.onError("Gagal menghapus ebook!")
                }
            }
        }
    }
}
