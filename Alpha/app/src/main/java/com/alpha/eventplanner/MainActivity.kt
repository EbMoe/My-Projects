package com.alpha.eventplanner

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.alpha.eventplanner.databinding.ActivityMainBinding
import java.util.*
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ShareCompat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply saved language setting before calling super.onCreate
        applySavedLocale()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting the toolbar as the action bar
        setSupportActionBar(binding.toolbar)

        // Initialize ViewPager2
        viewPager = binding.viewPager

        // Set up the ViewPagerAdapter
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        // Set up the BottomNavigationView
        setupBottomNavigationView()

        // Handle page changes to update BottomNavigationView
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigationView.menu.getItem(position).isChecked = true
            }
        })

        // Request notification permission if necessary (Android 13+)
        requestNotificationPermission()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                return true
            }
            R.id.action_faq -> {
                startActivity(Intent(this, FaqActivity::class.java))
                return true
            }
            R.id.action_privacy -> {
                startActivity(Intent(this, PrivacyPolicyActivity::class.java))
                return true
            }
            R.id.action_share -> {
                shareEventDetails() // Call function to share event details
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Share event details
    private fun shareEventDetails() {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setType("text/plain")
            .setChooserTitle("Share Event Details")
            .setText("Start creating events with Alpha Event Planner, available in the Google Play Store!")
            .intent
        if (shareIntent.resolveActivity(packageManager) != null) {
            startActivity(shareIntent)
        }
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    viewPager.currentItem = 0
                    true
                }
                R.id.calendar -> {
                    viewPager.currentItem = 1
                    true
                }
                R.id.map -> {
                    // Refresh the map fragment by detaching and reattaching it
                    val mapFragment = supportFragmentManager.findFragmentByTag("f2") as? MapFragment
                    mapFragment?.let {
                        supportFragmentManager.beginTransaction().detach(it).commitNow()
                        supportFragmentManager.beginTransaction().attach(it).commitNow()
                    }
                    viewPager.currentItem = 2
                    true
                }
                R.id.settings -> {
                    viewPager.currentItem = 3
                    true
                }
                else -> false
            }
        }
        // Set default selection
        binding.bottomNavigationView.selectedItemId = R.id.home
    }

    private fun applySavedLocale() {
        val preferences: SharedPreferences = getSharedPreferences("user_settings", Context.MODE_PRIVATE)
        val savedLanguage = preferences.getString("language", "English") ?: "English"

        val locale = when (savedLanguage) {
            "Zulu" -> Locale("zu")
            "Afrikaans" -> Locale("af")
            "English" -> Locale("en")
            else -> Locale.getDefault()
        }

        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)

        // Apply locale to configuration for API levels above 17
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            createConfigurationContext(config)
        } else {
            @Suppress("DEPRECATION")
            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }

    // Request notification permission for Android 13 and above
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request the POST_NOTIFICATIONS permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_CODE_NOTIFICATION
                )
            }
        }
    }

    // Handle the permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_NOTIFICATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("MainActivity", "Notification permission granted")
            } else {
                Log.d("MainActivity", "Notification permission denied")
            }
        }
    }

    // Override attachBaseContext to apply locale consistently across activities
    override fun attachBaseContext(newBase: Context) {
        val preferences: SharedPreferences = newBase.getSharedPreferences("user_settings", Context.MODE_PRIVATE)
        val savedLanguage = preferences.getString("language", "English") ?: "English"

        val locale = when (savedLanguage) {
            "Zulu" -> Locale("zu")
            "Afrikaans" -> Locale("af")
            "English" -> Locale("en")
            else -> Locale.getDefault()
        }

        Locale.setDefault(locale)
        val config = newBase.resources.configuration
        config.setLocale(locale)

        val newContext = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            newBase.createConfigurationContext(config)
        } else {
            @Suppress("DEPRECATION")
            newBase.resources.updateConfiguration(config, newBase.resources.displayMetrics)
            newBase
        }

        super.attachBaseContext(newContext)
    }

    companion object {
        private const val REQUEST_CODE_NOTIFICATION = 1001
    }
}
