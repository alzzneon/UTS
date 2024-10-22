package com.project.uts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.uts.databinding.ListBukuBinding

class BukuAdapter(
    private val books: List<Buku>,
    private val onEditClick: (Buku) -> Unit,
    private val onDeleteClick: (Buku) -> Unit
) : RecyclerView.Adapter<BukuAdapter.BukuViewHolder>() {

    inner class BukuViewHolder(private val binding: ListBukuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(buku: Buku) {
            binding.tvJudulBuku.text = buku.judul
            binding.tvKategoriBuku.text = buku.kategori
            binding.tvNamaAuthor.text = buku.penulis

            binding.ibEdit.setOnClickListener {
                onEditClick(buku)
            }
            binding.ibDelete.setOnClickListener {
                onDeleteClick(buku)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListBukuBinding.inflate(inflater, parent, false)
        return BukuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BukuViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int = books.size
}
