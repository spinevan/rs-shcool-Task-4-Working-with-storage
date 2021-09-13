package com.example.task4workingwithstorage.repositorys

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.task4workingwithstorage.dao.ServiceRequestDAO
import com.example.task4workingwithstorage.interfaces.IServiceRequestDAO
import com.example.task4workingwithstorage.models.ServiceRequest
import com.example.task4workingwithstorage.room.MySQLiteOpenHelper
import com.example.task4workingwithstorage.room.RoomSingleton

class ServiceRequestRepository() {

    private var application: Application? = null
    private var context: Context? = null
    //private val serviceRequestDAO: IServiceRequestDAO = db.serviceRequestDao()
    private var serviceRequestDAO: IServiceRequestDAO? = null

    constructor(application: Application, context: Context) : this() {
        this.application = application
        this.context = context

        val db = RoomSingleton.getInstance(application)
        this.serviceRequestDAO = db.serviceRequestDao()
    }

    constructor(application: Application, context: Context, useCursor: Boolean) : this() {
        this.application = application
        this.context = context

        val db = MySQLiteOpenHelper(context)
        this.serviceRequestDAO = db.serviceRequestDao()
    }

    //private val db = RoomSingleton.getInstance(application!!)


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

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(serviceRequest: ServiceRequest) {
        serviceRequestDAO!!.insert(serviceRequest)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(serviceRequest: ServiceRequest) {
        serviceRequestDAO!!.delete(serviceRequest)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getById(id: Long): ServiceRequest{
       return serviceRequestDAO!!.getById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(serviceRequest: ServiceRequest) {
        serviceRequestDAO!!.update(serviceRequest)
    }

}