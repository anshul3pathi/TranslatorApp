package com.example.translatorapp.ui.phrasebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.core.data.entities.HindiAndEnglishTranslation
import com.example.translatorapp.R
import com.example.translatorapp.databinding.FragmentPhraseBookBinding
import com.example.translatorapp.ui.adapters.RecentWordsAdapter
import com.example.translatorapp.ui.home.RecentWordOnclickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhraseBookFragment : Fragment(), RecentWordOnclickListener {

    private val viewModel: PhraseBookViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPhraseBookBinding.inflate(inflater)

        val recentWordsAdapter = RecentWordsAdapter(this)
        binding.phrasebookRV.adapter = recentWordsAdapter

        viewModel.translations.observe(viewLifecycleOwner, Observer {
            recentWordsAdapter.submitList(it)
        })

        return binding.root
    }

    override fun onWordClicked(translation: HindiAndEnglishTranslation) {
        val action = PhraseBookFragmentDirections.actionPhrasebookToHome(translation)
        this.findNavController().navigate(action)
    }

    override fun onStarClicked(translation: HindiAndEnglishTranslation) {
        translation.hindi.favourite = !translation.hindi.favourite
        viewModel.updateFavourite(translation.hindi)
    }

}