package com.example.translatorapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.translatorapp.R
import com.example.translatorapp.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navController = this.findNavController(R.id.nav_host_fragment)
        val drawer = binding.drawer
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.phraseBookFragment,
                R.id.settingsFragment
            ), drawer
        )
        val toolbar = binding.toolbar
        val navigationView = binding.navigationView
        setSupportActionBar(toolbar)

        toolbar.setupWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)

    }
}