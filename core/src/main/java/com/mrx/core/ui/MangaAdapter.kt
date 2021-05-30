package com.mrx.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrx.core.R
import com.mrx.core.databinding.ItemMangaBinding
import com.mrx.core.domain.model.Manga

class MangaAdapter : RecyclerView.Adapter<MangaAdapter.ViewHolder>() {

    private var listData = ArrayList<Manga>()
    var onItemClick: ((Manga) -> Unit)? = null

    fun setData(newListData: List<Manga>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_manga, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMangaBinding.bind(itemView)
        fun bind(data: Manga) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.imageUrl)
                    .into(imgManga)
                tvTitle.text = data.title
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[absoluteAdapterPosition])
            }
        }
    }
}