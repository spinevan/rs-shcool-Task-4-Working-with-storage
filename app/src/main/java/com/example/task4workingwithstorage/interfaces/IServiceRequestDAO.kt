package com.example.task4workingwithstorage.interfaces

import androidx.lifecycle.LiveData
import com.example.task4workingwithstorage.models.ServiceRequest

interface IServiceRequestDAO {
    fun allServiceRequests(): LiveData<List<ServiceRequest>>
    fun allServiceRequestsSortByName(): LiveData<List<ServiceRequest>>
    fun allServiceRequestsSortByDate(): LiveData<List<ServiceRequest>>
    fun allServiceRequestsSortByMaster(): LiveData<List<ServiceRequest>>
    suspend fun insert(serviceRequests: ServiceRequest)
    suspend fun delete(serviceRequests: ServiceRequest)
    fun getById(id: Long?): ServiceRequest
    suspend fun update(serviceRequests: ServiceRequest)
}