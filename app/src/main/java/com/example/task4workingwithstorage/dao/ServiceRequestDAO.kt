package com.example.task4workingwithstorage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.task4workingwithstorage.models.ServiceRequest

@Dao
interface ServiceRequestDAO{
    @Query("SELECT * FROM service_requests_table ORDER BY id DESC")
    fun allServiceRequests(): LiveData<List<ServiceRequest>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(serviceRequests: ServiceRequest)

    @Delete
    suspend fun delete(serviceRequests: ServiceRequest)

}