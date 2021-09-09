package com.example.task4workingwithstorage.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.task4workingwithstorage.models.ServiceRequest
import com.example.task4workingwithstorage.repositorys.ServiceRequestRepository
import com.example.task4workingwithstorage.room.RoomSingleton
import kotlinx.coroutines.launch

class ServiceRequestViewModel(application: Application): AndroidViewModel(application){
//    private val db: RoomSingleton = RoomSingleton.getInstance(application)
//
//    internal val allServiceRequests : LiveData<List<ServiceRequest>> = db.serviceRequestDao().allServiceRequests()
//
//    fun insert(serviceRequest:ServiceRequest){
//        db.serviceRequestDao().insert(serviceRequest)
//    }

    private val _serviceRequestRepository: ServiceRequestRepository = ServiceRequestRepository(application)

    val allServiceRequests: LiveData<List<ServiceRequest>> = _serviceRequestRepository.allServiceRequests()


    fun insert(serviceRequest: ServiceRequest) {
        viewModelScope.launch {
            _serviceRequestRepository.insert(serviceRequest)
        }
    }

    fun delete(serviceRequest: ServiceRequest) {
        viewModelScope.launch {
            _serviceRequestRepository.delete(serviceRequest)
        }
    }

    suspend fun getById(id: Long) :ServiceRequest {
        return _serviceRequestRepository.getById(id)
    }

    fun update(serviceRequest: ServiceRequest) {
        viewModelScope.launch {
            _serviceRequestRepository.update(serviceRequest)
        }
    }

        //_serviceRequestRepository.insert(serviceRequest)



}