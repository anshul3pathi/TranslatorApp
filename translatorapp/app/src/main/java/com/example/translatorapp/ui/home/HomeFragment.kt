package com.example.translatorapp.ui.home

import android.content.*
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import android.widget.PopupMenu
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.core.data.entities.HindiAndEnglishTranslation
import com.example.translatorapp.R
import com.example.translatorapp.databinding.FragmentHomeBinding
import com.example.translatorapp.ui.adapters.EnglishTranslationAdapter
import com.example.translatorapp.ui.adapters.RecentWordsAdapter
import com.example.translatorapp.utils.Constants
import com.example.translatorapp.utils.displaySnackBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), RecentWordOnclickListener, PopupMenu.OnMenuItemClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var inputWordTextField: TextInputLayout
    private lateinit var binding: FragmentHomeBinding
    private var currentTranslation: HindiAndEnglishTranslation? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater)

        inputWordTextField = binding.inputWordEditText
        setTextWatcher()

        binding.copyHomeIV.setOnClickListener {
            copyTextToClipBoard(currentTranslation!!.hindi.hindiTranslation)
        }
        binding.showMoreHomeIV.setOnClickListener {
            showPopup(it!!)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recentWordsAdapter = RecentWordsAdapter(this)
        binding.recentWordsRV.adapter = recentWordsAdapter

        val englishTranslationAdapter = EnglishTranslationAdapter()
        binding.englishTranslationsHomeRV.adapter = englishTranslationAdapter

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.translation.observe(viewLifecycleOwner, {
            currentTranslation = it
            englishTranslationAdapter.submitList(it.englishTranslations)
        })
        viewModel.translations.observe(viewLifecycleOwner, {
            recentWordsAdapter.submitList(it)
        })
        viewModel.snackBarMessage.observe(viewLifecycleOwner, {
            if (it != null) {
                displaySnackBar(it, binding.parentHomeCL)
            }
        })

        arguments?.let {
            val translation = HomeFragmentArgs.fromBundle(it).hindiAndEnglishTranslation
            inputWordTextField.editText?.setText(translation!!.hindi.word)
        }
    }

    override fun onWordClicked(translation: HindiAndEnglishTranslation) {
        inputWordTextField.editText?.setText(translation.hindi.word)
    }

    override fun onStarClicked(translation: HindiAndEnglishTranslation) {
        translation.hindi.favourite = !translation.hindi.favourite
        viewModel.updateFavouriteFromRecentWords(translation.hindi)
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share_option -> {
                val sendTextIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, currentTranslation!!.hindi.hindiTranslation)
                    type = "text/plain"
                }
                try {
                    startActivity(sendTextIntent)
                } catch (e: ActivityNotFoundException) {
                    Log.d(Constants.HOME_FRAGMENT_LOG_TAG, "No app found to share text")
                }
                true
            } else -> false
        }
    }

    private fun showPopup(view: View) {
        PopupMenu(requireContext(), view).apply {
            setOnMenuItemClickListener(this@HomeFragment)
            inflate(R.menu.hindi_translation_popup_menu)
            show()
        }
    }

    private fun copyTextToClipBoard(text: String) {
        val clipBoard =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("HindiTranslation", text)
        clipBoard.setPrimaryClip(clipData)

        displaySnackBar("Translation copied", binding.parentHomeCL)
    }

    private fun setTextWatcher() {
        inputWordTextField.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    viewModel.searchBoxEmpty()
                } else {
                    viewModel.typingInSearchBox()
                }

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) {
                    viewModel.cancelGettingTranslation()
                    viewModel.getTranslation(s.toString().trim().replace("\n", ""))
                    Log.d("${Constants.HOME_FRAGMENT_LOG_TAG} After", s.toString())
                }
            }
        })
    }

}