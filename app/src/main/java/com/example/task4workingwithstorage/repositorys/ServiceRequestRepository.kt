package com.example.task4workingwithstorage.repositorys

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.task4workingwithstorage.dao.ServiceRequestDAO
import com.example.task4workingwithstorage.models.ServiceRequest
import com.example.task4workingwithstorage.room.RoomSingleton

class ServiceRequestRepository(application: Application) {
    private val db: RoomSingleton = RoomSingleton.getInstance(application)
    private val serviceRequestDAO: ServiceRequestDAO = db.serviceRequestDao()

    fun allServiceRequests(): LiveData<List<ServiceRequest>> {
       return db.serviceRequestDao().allServiceRequests()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(serviceRequest: ServiceRequest) {
        db.serviceRequestDao().insert(serviceRequest)
    }

}