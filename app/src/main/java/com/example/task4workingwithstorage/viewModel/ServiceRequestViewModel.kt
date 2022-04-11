package com.example.task4workingwithstorage.viewModel

import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.task4workingwithstorage.USE_CURSOR_FIRST
import com.example.task4workingwithstorage.models.ServiceRequest
import com.example.task4workingwithstorage.repositorys.ServiceRequestRepository
import com.example.task4workingwithstorage.ui.main.adapters.RecyclerViewAdapter
import kotlinx.coroutines.*

class ServiceRequestViewModel(application: Application): AndroidViewModel(application){

    var useCursor = USE_CURSOR_FIRST
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val bgDispatcher: CoroutineDispatcher = Dispatchers.IO

    init {
        val sharedPrefs =  PreferenceManager.getDefaultSharedPreferences(getApplication<Application>().applicationContext)
        useCursor = sharedPrefs.getBoolean("useCursor", USE_CURSOR_FIRST)
        changeDAO()
    }

    private var _serviceRequestRepository: ServiceRequestRepository? = null

    var allServiceRequests: LiveData<List<ServiceRequest>> = _serviceRequestRepository!!.allServiceRequests(getApplication<Application>().applicationContext)

    fun delete(serviceRequest: ServiceRequest) {
        viewModelScope.launch {
            _serviceRequestRepository!!.serviceRequestDAO!!.delete(serviceRequest)
        }
    }

    fun getAll(): LiveData<List<ServiceRequest>> {
        return _serviceRequestRepository!!.allServiceRequests(getApplication<Application>().applicationContext)
    }

    fun changeDAO() {

        if ( useCursor ) {
            _serviceRequestRepository = ServiceRequestRepository(getApplication<Application>(), getApplication<Application>().applicationContext, true)
        } else {
            _serviceRequestRepository = ServiceRequestRepository(getApplication<Application>(), getApplication<Application>().applicationContext, false)
        }
    }

    fun readAllServiceRequests() {
        allServiceRequests = _serviceRequestRepository!!.allServiceRequests(getApplication<Application>().applicationContext)
    }

    fun updateViewData(viewLifecycleOwner: LifecycleOwner, recyclerViewAdapter: RecyclerViewAdapter) = uiScope.launch {
        //showLoading // ui thread
        val updatedServiceRequest = withContext(bgDispatcher) { // background thread
            return@withContext getAll()
        }
        updatedServiceRequest.observe(viewLifecycleOwner, { serviceRequests->
            recyclerViewAdapter.submitList(serviceRequests)
        })
    }

}