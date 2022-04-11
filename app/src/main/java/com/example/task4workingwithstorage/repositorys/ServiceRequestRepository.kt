package com.example.task4workingwithstorage.repositorys

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.task4workingwithstorage.interfaces.IServiceRequestDAO
import com.example.task4workingwithstorage.models.ServiceRequest
import com.example.task4workingwithstorage.room.MySQLiteOpenHelper
import com.example.task4workingwithstorage.room.RoomSingleton

class ServiceRequestRepository() {

    private var application: Application? = null
    private var context: Context? = null
    var serviceRequestDAO: IServiceRequestDAO? = null

    constructor(application: Application, context: Context, useCursor: Boolean) : this() {
        this.application = application
        this.context = context

        if (useCursor) {
           this.serviceRequestDAO = MySQLiteOpenHelper(context).ServiceRequestDao()
        } else {
            this.serviceRequestDAO = RoomSingleton.getInstance(application).serviceRequestDao()
        }
    }

    fun allServiceRequests(context: Context): LiveData<List<ServiceRequest>> {

       val sharedPrefs =  PreferenceManager.getDefaultSharedPreferences(context);
       val sortBy = sharedPrefs.getString("sortingBy", "id")
        when (sortBy) {
            "master" -> return serviceRequestDAO!!.allServiceRequestsSortByMaster()
            "name" -> return serviceRequestDAO!!.allServiceRequestsSortByName()
            "dateTime" -> return serviceRequestDAO!!.allServiceRequestsSortByDate()
        }

       return serviceRequestDAO!!.allServiceRequests()
    }
}