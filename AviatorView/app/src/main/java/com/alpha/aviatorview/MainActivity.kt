package com.alpha.aviatorview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Load MapFragment by default
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MapFragment())
                .commit()
        }

        // Handle navigation item clicks
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_map -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MapFragment())
                        .commit()
                    true
                }
                R.id.nav_flights -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, FlightsFragment())
                        .commit()
                    true
                }
                R.id.nav_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, SearchFragment())
                        .commit()
                    true
                }
                R.id.nav_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, SettingsFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}