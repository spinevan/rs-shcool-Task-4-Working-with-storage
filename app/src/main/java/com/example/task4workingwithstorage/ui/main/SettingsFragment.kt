package com.example.task4workingwithstorage.ui.main

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.task4workingwithstorage.R

class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        const val canonicalName = "com.example.task4workingwithstorage.ui.main.SettingsFragment"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}