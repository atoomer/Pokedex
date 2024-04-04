package com.ser210.pokedexv3

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.ser210.pokedexv3.databinding.ActivityMainBinding

/**
 * Main activity for the app
 */
class MainActivity : AppCompatActivity() {

    // Variables to keep track of the current background and text color
    private var isBackgroundDark = false
    private var isTextDark = true

    // view binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setup view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // toolbar setup
        setSupportActionBar(binding.toolbar)

        // navigation controller setup
        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController
        val builder = AppBarConfiguration.Builder(navController.graph)
        val appBarConfiguration = builder.build()
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    // inflate options menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.help_menu, menu)
        menuInflater.inflate(R.menu.share_menu, menu)
        menuInflater.inflate(R.menu.settings_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // set up options menu actions
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            // share button
            R.id.shareButton -> {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Your text to share")
                    type = "text/plain"
                }
                // Start the share intent
                startActivity(Intent.createChooser(shareIntent, null))
                true
            }

            // settings button
            R.id.settingsButton -> {
                val rootView = binding.root
                if (isBackgroundDark) {
                    rootView.setBackgroundColor(Color.WHITE)
                } else {
                    rootView.setBackgroundColor(Color.DKGRAY)
                }
                isBackgroundDark = !isBackgroundDark
                val sharedPreferences = getSharedPreferences("appPreferences", MODE_PRIVATE)
                val isTextDark = sharedPreferences.getBoolean("isTextDark", false)
                with (sharedPreferences.edit()) {
                    putBoolean("isTextDark", !isTextDark)
                    apply()
                }
                true


            }

            // help button
            R.id.helpButton -> {
                // Navigate to HelpFragment
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.helpFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}