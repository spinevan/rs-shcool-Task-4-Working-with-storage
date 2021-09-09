package com.example.task4workingwithstorage.interfaces

import com.example.task4workingwithstorage.models.ServiceRequest

interface IMainActivityNav {

    fun openCreateFragment()
    fun openUpdateFragment(id: Long)
    fun openPreferencesFragment()

}