package com.project.uts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.uts.databinding.ListEbookBinding

class EbookAdapter(
    private var ebooks: List<Ebook>,
    private val onEditClick: (Ebook) -> Unit,
    private val onDeleteClick: (Ebook) -> Unit
) : RecyclerView.Adapter<EbookAdapter.EbookViewHolder>() {

    inner class EbookViewHolder(private val binding: ListEbookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ebook: Ebook) {
            binding.ebookNamaTextView.text = ebook.namaeb
            binding.ebookPenerbitTextView.text = ebook.penerbiteb
            binding.ebookJenisTextView.text = ebook.jeniseb

            binding.ibEditEbook.setOnClickListener {
                onEditClick(ebook)
            }

            binding.ibDeleteEbook.setOnClickListener {
                onDeleteClick(ebook)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EbookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListEbookBinding.inflate(inflater, parent, false)
        return EbookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EbookViewHolder, position: Int) {
        holder.bind(ebooks[position])
    }

    override fun getItemCount(): Int = ebooks.size
}
