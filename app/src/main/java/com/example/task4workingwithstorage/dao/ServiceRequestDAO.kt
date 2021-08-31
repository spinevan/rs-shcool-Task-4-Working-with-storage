package com.example.task4workingwithstorage.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.task4workingwithstorage.models.ServiceRequest

@Dao
interface ServiceRequestDAO{
    @Query("SELECT * FROM service_requests_table ORDER BY id DESC")
    fun allServiceRequests(): LiveData<List<ServiceRequest>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(serviceRequests: ServiceRequest)
}