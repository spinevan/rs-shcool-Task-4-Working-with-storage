package com.example.task4workingwithstorage.interfaces

import com.example.task4workingwithstorage.models.ServiceRequest

interface IServiceRequestEditorListener {

    fun delete(serviceRequest: ServiceRequest)
    fun open(id: Long)

}