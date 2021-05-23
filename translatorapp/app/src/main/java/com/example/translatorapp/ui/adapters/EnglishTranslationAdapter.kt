package com.example.translatorapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.translatorapp.databinding.ItemEnglishTranslationBinding
import com.example.core.data.entities.EnglishTranslation
import com.example.core.data.EnglishTranslationItems

class EnglishTranslationAdapter :
    ListAdapter<EnglishTranslation, EnglishTranslationAdapter.TranslationViewHolder>(EnglishTranslationDiffCallBack()) {

    private var inflater: LayoutInflater? = null

    inner class TranslationViewHolder(
        private val binding: ItemEnglishTranslationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val englishTranslationItemAdapter by lazy {
            val adapter = EnglishTranslationItemAdapter().apply {
                stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }
            binding.englishTranslationsRV.adapter = adapter
            adapter
        }

        fun bind(englishTranslation: EnglishTranslation) {
            binding.englishTranslation = englishTranslation
            val englishTranslationItemsList = mutableListOf<EnglishTranslationItems>()
            for (i in englishTranslation.translations.indices) {
                englishTranslationItemsList.add(
                    EnglishTranslationItems(
                        i.toLong(),
                        englishTranslation.translations[i],
                        englishTranslation.examples[i]
                    )
                )
            }
            englishTranslationItemAdapter.submitList(englishTranslationItemsList)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranslationViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = ItemEnglishTranslationBinding.inflate(inflater!!, parent, false)
        return TranslationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TranslationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class EnglishTranslationDiffCallBack : DiffUtil.ItemCallback<EnglishTranslation>() {

    override fun areItemsTheSame(oldItem: EnglishTranslation, newItem: EnglishTranslation): Boolean {
        return newItem == oldItem
    }

    override fun areContentsTheSame(oldItem: EnglishTranslation, newItem: EnglishTranslation): Boolean {
        return oldItem == newItem
    }

}
