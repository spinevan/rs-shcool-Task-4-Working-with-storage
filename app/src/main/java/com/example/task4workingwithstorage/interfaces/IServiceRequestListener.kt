package com.example.task4workingwithstorage.interfaces

import com.example.task4workingwithstorage.models.ServiceRequest

interface IServiceRequestListener {

    fun delete(serviceRequest: ServiceRequest)

    fun open(id: Long)

}