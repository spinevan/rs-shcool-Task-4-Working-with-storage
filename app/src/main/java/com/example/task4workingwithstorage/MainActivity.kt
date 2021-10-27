package com.example.task4workingwithstorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.task4workingwithstorage.interfaces.IMainActivityNav
import com.example.task4workingwithstorage.ui.main.CreateUpdateFragment
import com.example.task4workingwithstorage.ui.main.MainFragment
import com.example.task4workingwithstorage.ui.main.SettingsFragment

class MainActivity : AppCompatActivity(), IMainActivityNav {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()

        }
    }

    override fun openCreateFragment() {
       openCreateUpdateFragment(null)
    }

    override fun openUpdateFragment(id: Long) {
        openCreateUpdateFragment(id)
    }

    private fun openCreateUpdateFragment(id: Long?) {
        val createUpdateFragment: CreateUpdateFragment = CreateUpdateFragment.newInstance(id)
        commitTransactionWithBackStack(createUpdateFragment, createUpdateFragment.canonicalName)
    }

    override fun openPreferencesFragment() {
        val fragment = SettingsFragment() as Fragment
        commitTransactionWithBackStack(fragment, SettingsFragment.canonicalName)
    }

    private fun commitTransactionWithBackStack(fragment: Fragment, canonicalName: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment).addToBackStack(canonicalName).commit()
    }
}