package com.example.task4workingwithstorage.viewModel

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.task4workingwithstorage.USE_CURSOR_FIRST
import com.example.task4workingwithstorage.models.ServiceRequest
import com.example.task4workingwithstorage.repositorys.ServiceRequestRepository
import com.example.task4workingwithstorage.ui.main.presentors.ServiceRequestPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CreateUpdateViwModel(application: Application): AndroidViewModel(application) {

    private val sharedPrefs: SharedPreferences =  PreferenceManager.getDefaultSharedPreferences(getApplication<Application>().applicationContext)
    private var _serviceRequestRepository: ServiceRequestRepository? = ServiceRequestRepository(getApplication<Application>(), getApplication<Application>().applicationContext, sharedPrefs.getBoolean("useCursor", USE_CURSOR_FIRST))

    var serviceRequestPresenter: LiveData<ServiceRequestPresenter>? = null
    var serviceRequest: ServiceRequest? = null

    private fun getById(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { // background thread
                serviceRequest = _serviceRequestRepository!!.serviceRequestDAO!!.getById(id)
                if ( serviceRequest != null ) {
                    serviceRequestPresenter =
                        MutableLiveData(ServiceRequestPresenter(serviceRequest!!))
                } else {
                    serviceRequestPresenter =
                        MutableLiveData(ServiceRequestPresenter(ServiceRequest(null,"", Date(), "")))
                }
            }
        }
    }

    fun insert(serviceRequest: ServiceRequest) {
        viewModelScope.launch {
            _serviceRequestRepository!!.serviceRequestDAO!!.insert(serviceRequest)
        }
    }

    fun update(serviceRequest: ServiceRequest) {
        viewModelScope.launch {
            _serviceRequestRepository!!.serviceRequestDAO!!.update(serviceRequest)
        }
    }

    fun loadServiceRequest(id: Long) {
            getById(id)
    }

}