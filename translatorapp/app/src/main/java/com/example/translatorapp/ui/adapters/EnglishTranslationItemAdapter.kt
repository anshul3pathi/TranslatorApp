package com.example.translatorapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.EnglishTranslationItems
import com.example.translatorapp.databinding.ItemTranslationExampleBinding

class EnglishTranslationItemAdapter :
    ListAdapter <
            EnglishTranslationItems,
            EnglishTranslationItemAdapter.EnglishTranslationItemViewHolder
    > (EnglishTranslationItemDiffUtil()) {

    inner class EnglishTranslationItemViewHolder(
        private val binding: ItemTranslationExampleBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(englishTranslationItem: EnglishTranslationItems) {
            binding.englishTranslationItem = englishTranslationItem
        }

    }

    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EnglishTranslationItemViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = ItemTranslationExampleBinding.inflate(inflater!!, parent, false)
        return EnglishTranslationItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EnglishTranslationItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class EnglishTranslationItemDiffUtil : DiffUtil.ItemCallback<EnglishTranslationItems>() {

    override fun areItemsTheSame(
        oldItem: EnglishTranslationItems,
        newItem: EnglishTranslationItems
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: EnglishTranslationItems,
        newItem: EnglishTranslationItems
    ): Boolean {
        return oldItem.id == newItem.id
    }

}

