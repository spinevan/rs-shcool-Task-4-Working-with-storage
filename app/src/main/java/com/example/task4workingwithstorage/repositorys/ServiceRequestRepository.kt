package com.example.task4workingwithstorage.repositorys

import android.app.Application
import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceManager
import com.example.task4workingwithstorage.dao.ServiceRequestDAO
import com.example.task4workingwithstorage.models.ServiceRequest
import com.example.task4workingwithstorage.room.RoomSingleton

class ServiceRequestRepository(application: Application) {
    private val db: RoomSingleton = RoomSingleton.getInstance(application)
    private val serviceRequestDAO: ServiceRequestDAO = db.serviceRequestDao()

    fun allServiceRequests(context: Context): LiveData<List<ServiceRequest>> {

       val sharedPrefs =  PreferenceManager.getDefaultSharedPreferences(context);
       val sortBy = sharedPrefs.getString("sortingBy", "id")

        when (sortBy) {
            "master" -> return serviceRequestDAO.allServiceRequestsSortByMaster()
            "name" -> return serviceRequestDAO.allServiceRequestsSortByName()
            "dateTime" -> return serviceRequestDAO.allServiceRequestsSortByDate()
        }

       return serviceRequestDAO.allServiceRequests()

    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(serviceRequest: ServiceRequest) {
        serviceRequestDAO.insert(serviceRequest)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(serviceRequest: ServiceRequest) {
        serviceRequestDAO.delete(serviceRequest)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getById(id: Long): ServiceRequest{
       return serviceRequestDAO.getById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(serviceRequest: ServiceRequest) {
        serviceRequestDAO.update(serviceRequest)
    }

}