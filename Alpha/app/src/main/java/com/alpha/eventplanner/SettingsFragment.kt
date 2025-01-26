package com.alpha.eventplanner

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.alpha.eventplanner.R
import com.google.android.material.switchmaterial.SwitchMaterial
import java.util.*

class SettingsFragment : Fragment() {

    private lateinit var preferences: SharedPreferences
    private lateinit var languageSpinner: Spinner
    private lateinit var pushNotificationSwitch: SwitchMaterial
    private lateinit var allowLocationSwitch: SwitchMaterial
    private lateinit var shareUsageDataSwitch: SwitchMaterial
    private lateinit var themeRadioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SettingsFragment", "onCreate called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("SettingsFragment", "onCreateView called")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Initialize SharedPreferences
        preferences = requireContext().getSharedPreferences("user_settings", Context.MODE_PRIVATE)
        Log.i("SettingsFragment", "SharedPreferences initialized")

        // Initialize UI elements
        pushNotificationSwitch = view.findViewById(R.id.pushNotificationSwitch)
        allowLocationSwitch = view.findViewById(R.id.allowLocationSwitch)
        shareUsageDataSwitch = view.findViewById(R.id.shareUsageDataSwitch)
        languageSpinner = view.findViewById(R.id.languageSpinner)
        themeRadioGroup = view.findViewById(R.id.themeRadioGroup)

        // Initialize the Logout button
        val logoutButton: Button = view.findViewById(R.id.logout_button)
        logoutButton.setOnClickListener {
            Log.d("SettingsFragment", "Logout button clicked")
            // Clear user-specific data from SharedPreferences
            val editor = preferences.edit()
            editor.remove("push_notifications")
            editor.remove("allow_location")
            editor.remove("share_usage_data")
            editor.putBoolean("is_logged_in", false)
            editor.apply()

            // Redirect to LoginActivity
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            // Show a logout success message
            Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
            Log.i("SettingsFragment", "User logged out and redirected to LoginActivity")
        }

        // Load saved settings
        loadSettings()

        // Set up listeners
        pushNotificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d("SettingsFragment", "Push Notification Switch changed: $isChecked")
            savePreference("push_notifications", isChecked)
            if (isChecked) {
                Toast.makeText(context, "Push Notifications Enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Push Notifications Disabled", Toast.LENGTH_SHORT).show()
            }
        }

        allowLocationSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d("SettingsFragment", "Allow Location Switch changed: $isChecked")
            savePreference("allow_location", isChecked)
            if (isChecked) {
                Toast.makeText(context, "Location Access Enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Location Access Disabled", Toast.LENGTH_SHORT).show()
            }
        }

        shareUsageDataSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d("SettingsFragment", "Share Usage Data Switch changed: $isChecked")
            savePreference("share_usage_data", isChecked)
            if (isChecked) {
                Toast.makeText(context, "Usage Data Sharing Enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Usage Data Sharing Disabled", Toast.LENGTH_SHORT).show()
            }
        }

        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedLanguage = resources.getStringArray(R.array.languages_array)[position]
                val currentLanguage = preferences.getString("language", "English")
                Log.d("SettingsFragment", "Language selected: $selectedLanguage")
                if (selectedLanguage != currentLanguage) {
                    savePreference("language", selectedLanguage)
                    updateLocale(selectedLanguage)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.d("SettingsFragment", "No language selected")
            }
        }

        // Set theme change listener
        themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.lightTheme -> setAppTheme(AppCompatDelegate.MODE_NIGHT_NO)
                R.id.darkTheme -> setAppTheme(AppCompatDelegate.MODE_NIGHT_YES)
                R.id.systemDefaultTheme -> setAppTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }

        return view
    }

    private fun setAppTheme(themeMode: Int) {
        Log.d("SettingsFragment", "Theme changed to mode: $themeMode")
        AppCompatDelegate.setDefaultNightMode(themeMode)
        savePreference("theme", themeMode)
        Toast.makeText(context, "Theme Changed", Toast.LENGTH_SHORT).show()
    }

    private fun updateLocale(language: String) {
        Log.d("SettingsFragment", "Locale updated to: $language")
        val locale = when (language) {
            "Zulu" -> Locale("zu")
            "Afrikaans" -> Locale("af")
            "English" -> Locale("en")
            else -> Locale.getDefault()
        }

        Locale.setDefault(locale)
        val config = requireContext().resources.configuration
        config.setLocale(locale)

        // Update the configuration
        requireContext().createConfigurationContext(config)
        requireActivity().recreate()  // Recreate activity to apply changes
        Log.i("SettingsFragment", "Locale changed and activity recreated")
    }

    private fun loadSettings() {
        Log.d("SettingsFragment", "Loading saved settings")
        // Load the saved settings and update the UI
        pushNotificationSwitch.isChecked = preferences.getBoolean("push_notifications", false)
        allowLocationSwitch.isChecked = preferences.getBoolean("allow_location", false)
        shareUsageDataSwitch.isChecked = preferences.getBoolean("share_usage_data", false)

        // Load selected language from preferences
        val savedLanguage = preferences.getString("language", "English")
        val languagePosition = resources.getStringArray(R.array.languages_array).indexOf(savedLanguage)
        languageSpinner.setSelection(languagePosition)
        Log.d("SettingsFragment", "Selected language loaded: $savedLanguage")

        // Load selected theme from preferences
        val savedTheme = preferences.getInt("theme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        when (savedTheme) {
            AppCompatDelegate.MODE_NIGHT_NO -> themeRadioGroup.check(R.id.lightTheme)
            AppCompatDelegate.MODE_NIGHT_YES -> themeRadioGroup.check(R.id.darkTheme)
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> themeRadioGroup.check(R.id.systemDefaultTheme)
        }
        Log.d("SettingsFragment", "Theme loaded: $savedTheme")
    }

    private fun savePreference(key: String, value: Any) {
        Log.d("SettingsFragment", "Saving preference: $key = $value")
        // Save the preference value in SharedPreferences
        with(preferences.edit()) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                is Int -> putInt(key, value)
            }
            apply()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}
