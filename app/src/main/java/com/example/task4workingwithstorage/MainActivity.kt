package com.example.task4workingwithstorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.task4workingwithstorage.interfaces.IMainActivityNav
import com.example.task4workingwithstorage.interfaces.IServiceRequestListener
import com.example.task4workingwithstorage.models.ServiceRequest
import com.example.task4workingwithstorage.ui.main.CreateUpdateFragment
import com.example.task4workingwithstorage.ui.main.MainFragment

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
        val createUpdateFragment: CreateUpdateFragment = CreateUpdateFragment.newInstance(null)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, createUpdateFragment)
            .addToBackStack(createUpdateFragment::class.simpleName)
            .commit()

    }

    override fun openUpdateFragment(id: String) {
        TODO("Not yet implemented")
    }

}