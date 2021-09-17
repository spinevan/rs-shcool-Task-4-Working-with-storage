package com.example.task4workingwithstorage.viewModel

import android.app.Application
import android.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.task4workingwithstorage.LOG_TAG
import com.example.task4workingwithstorage.USE_CURSOR_FIRST
import com.example.task4workingwithstorage.models.ServiceRequest
import com.example.task4workingwithstorage.repositorys.ServiceRequestRepository
import com.example.task4workingwithstorage.room.RoomSingleton
import kotlinx.coroutines.launch

class ServiceRequestViewModel(application: Application): AndroidViewModel(application){

    var useCursor = USE_CURSOR_FIRST

    init {
        val sharedPrefs =  PreferenceManager.getDefaultSharedPreferences(getApplication<Application>().applicationContext);
        useCursor = sharedPrefs.getBoolean("useCursor", USE_CURSOR_FIRST)
        changeDAO()
    }

    private var _serviceRequestRepository: ServiceRequestRepository? = null

    var allServiceRequests: LiveData<List<ServiceRequest>> = _serviceRequestRepository!!.allServiceRequests(getApplication<Application>().applicationContext)

    fun insert(serviceRequest: ServiceRequest) {
        viewModelScope.launch {
            _serviceRequestRepository!!.insert(serviceRequest)
        }
    }

    fun delete(serviceRequest: ServiceRequest) {
        viewModelScope.launch {
            _serviceRequestRepository!!.delete(serviceRequest)
        }
    }

    suspend fun getById(id: Long) :ServiceRequest {
        return _serviceRequestRepository!!.getById(id)
    }

    fun update(serviceRequest: ServiceRequest) {
        viewModelScope.launch {
            _serviceRequestRepository!!.update(serviceRequest)
        }
    }

    suspend fun getAll(): LiveData<List<ServiceRequest>> {
        return _serviceRequestRepository!!.allServiceRequests(getApplication<Application>().applicationContext)
    }

    fun changeDAO() {

        if ( useCursor ) {
            _serviceRequestRepository = ServiceRequestRepository(getApplication<Application>(), getApplication<Application>().applicationContext, true)
        } else {
            _serviceRequestRepository = ServiceRequestRepository(getApplication<Application>(), getApplication<Application>().applicationContext)
        }

    }


}