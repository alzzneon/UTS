package com.project.uts

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.project.uts.databinding.ActivityTambahpengunjungBinding

class TambahPengujungActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTambahpengunjungBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tambahpengunjung)

        binding.btntambahpengguna.setOnClickListener {
            addPengunjung()  // Memanggil fungsi untuk menambahkan pengunjung
        }

        binding.buttonkembali.setOnClickListener {
            finish() // Mengakhiri activity ini dan kembali ke activity sebelumnya
        }
    }

    private fun addPengunjung() {
        val nama = binding.etnama.text.toString()
        val jenisKelamin = binding.etjk.text.toString()
        val alamat = binding.etalamat.text.toString()

        // Validasi input
        if (TextUtils.isEmpty(nama)) {
            binding.etnama.error = "Nama diperlukan"
            return
        }
        if (TextUtils.isEmpty(jenisKelamin)) {
            binding.etjk.error = "Jenis Kelamin diperlukan"
            return
        }
        if (TextUtils.isEmpty(alamat)) {
            binding.etalamat.error = "Alamat diperlukan"
            return
        }

        // Buat objek Pengunjung baru
        val newPengunjung = Pengunjung(
            nama = nama,
            jk = jenisKelamin,
            alamat = alamat
        )

        // Simpan pengunjung ke database
        CoroutineScope(Dispatchers.IO).launch {
            val db = BukuDatabase.getDatabase(applicationContext)
            db.pengunjungDao().insertPengunjung(newPengunjung)  // Panggil fungsi insertPengunjung
            withContext(Dispatchers.Main) {
                Toast.makeText(this@TambahPengujungActivity, "Pengunjung $nama berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                finish()  // Kembali ke halaman sebelumnya setelah selesai
            }
        }
    }
}
