package com.example.translatorapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.entities.HindiAndEnglishTranslation
import com.example.translatorapp.R
import com.example.translatorapp.databinding.ItemWordBinding
import com.example.translatorapp.ui.home.RecentWordOnclickListener

class RecentWordsAdapter(
    private val touchListener: RecentWordOnclickListener
) :
    ListAdapter<HindiAndEnglishTranslation, RecentWordsAdapter.RecentWordsViewHolder>(TranslationDiffCallBack()) {

    private var inflater: LayoutInflater? = null

    inner class RecentWordsViewHolder(
        private val binding: ItemWordBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                wordsContainerItemWordLL.setOnClickListener {
                    touchListener.onWordClicked(getItem(layoutPosition))
                }
                favouriteItemIV.setOnClickListener {
                    it as ImageView
                    val translation = getItem(layoutPosition)
                    touchListener.onStarClicked(translation)
                    this.favourite = translation.hindi.favourite
                }
            }
        }

        fun bind(hindiAndEnglishTranslation: HindiAndEnglishTranslation) {
            binding.translation = hindiAndEnglishTranslation
            binding.favourite = hindiAndEnglishTranslation.hindi.favourite
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentWordsViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = ItemWordBinding.inflate(inflater!!, parent, false)
        return RecentWordsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentWordsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class TranslationDiffCallBack : DiffUtil.ItemCallback<HindiAndEnglishTranslation>() {

    override fun areItemsTheSame(oldItem: HindiAndEnglishTranslation, newItem: HindiAndEnglishTranslation): Boolean {
        return newItem.hindi.word == oldItem.hindi.word
    }

    override fun areContentsTheSame(oldItem: HindiAndEnglishTranslation, newItem: HindiAndEnglishTranslation): Boolean {
        return oldItem == newItem
    }

}