package com.example.task4workingwithstorage.ui.main.presentors

import android.icu.text.SimpleDateFormat
import com.example.task4workingwithstorage.models.ServiceRequest
import java.util.*

class ServiceRequestPresenter(serviceRequest: ServiceRequest) {

    var name: String = serviceRequest.name
    var master = serviceRequest.master
    var dateTime: Date? = serviceRequest.dateTime

    var date: String = ""
    var time: String = ""
    private val formatterDate = SimpleDateFormat("yyyy-MM-dd")
    private val formatterTime = SimpleDateFormat("HH-mm")

    init {
        setTextDate()
        setTextTime()
    }

    private fun setTextDate() {
        date = formatterDate.format(dateTime)
    }

    private fun setTextTime() {
        time = formatterTime.format(dateTime)
    }

}