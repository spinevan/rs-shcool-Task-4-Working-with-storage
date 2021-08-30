package com.example.task4workingwithstorage.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.task4workingwithstorage.models.ServiceRequest
import com.example.task4workingwithstorage.room.RoomSingleton

class ServiceRequestViewModel(application: Application): AndroidViewModel(application){
    private val db: RoomSingleton = RoomSingleton.getInstance(application)

    internal val allServiceRequests : LiveData<List<ServiceRequest>> = db.serviceRequestDao().allServiceRequests()

    fun insert(serviceRequest:ServiceRequest){
        db.serviceRequestDao().insert(serviceRequest)
    }
}