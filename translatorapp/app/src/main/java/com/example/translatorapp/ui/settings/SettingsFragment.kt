package com.example.translatorapp.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.example.core.AppTheme
import com.example.translatorapp.R
import com.example.translatorapp.databinding.FragmentSettingsBinding
import com.example.translatorapp.utils.Constants
import com.example.translatorapp.utils.displaySnackBar
import com.example.translatorapp.utils.setAppTheme
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var binding: FragmentSettingsBinding
    private var appTheme: AppTheme = AppTheme.LIGHT

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.deleteRecentSettingContainer.setOnClickListener {
            deleteRecentClicked()
        }
        binding.themeSettingContainer.setOnClickListener {
            themeSettingsClicked()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.appTheme.observe(viewLifecycleOwner, { appTheme ->
            this.appTheme = appTheme
            setAppTheme(appTheme)
        })
    }

    private fun themeSettingsClicked() {
        val context = requireContext()
        val singleItems = arrayOf("Light", "Dark")
        val checkedItem = if (appTheme == AppTheme.LIGHT) 0 else 1
        var itemChecked = checkedItem
        MaterialAlertDialogBuilder(context)
            .setTitle(context.resources.getString(R.string.theme_settings_title))
            .setNeutralButton(context.resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(context.resources.getString(R.string.ok)) { _, _ ->
                if (itemChecked == 0) {
                    viewModel.changeTheme(AppTheme.LIGHT)
                } else {
                    viewModel.changeTheme(AppTheme.DARK)
                }
            }
            .setSingleChoiceItems(singleItems, checkedItem) { _, which ->
                Log.d(Constants.SETTINGS_FRAGMENT_LOG_TAG, "$which")
                itemChecked = which
            }
            .show()
    }

    private fun deleteRecentClicked() {
        val context = requireContext()
        MaterialAlertDialogBuilder(context)
            .setTitle(context.resources.getString(R.string.delete_recent_settings_title))
            .setNeutralButton(context.resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(context.resources.getString(R.string.ok)) { _, _ ->
                viewModel.clearAllTranslations()
                displaySnackBar("Recent translations cleared", binding.parentSettingsLL)
            }
            .show()
    }

}