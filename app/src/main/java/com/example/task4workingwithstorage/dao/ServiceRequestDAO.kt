package com.example.task4workingwithstorage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.task4workingwithstorage.TABLE_NAME
import com.example.task4workingwithstorage.interfaces.IServiceRequestDAO
import com.example.task4workingwithstorage.models.ServiceRequest

@Dao
interface ServiceRequestDAO: IServiceRequestDAO {
    @Query("SELECT * FROM $TABLE_NAME ORDER BY id DESC")
    override fun allServiceRequests(): LiveData<List<ServiceRequest>>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY name")
    override fun allServiceRequestsSortByName(): LiveData<List<ServiceRequest>>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY date_time")
    override fun allServiceRequestsSortByDate(): LiveData<List<ServiceRequest>>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY master")
    override fun allServiceRequestsSortByMaster(): LiveData<List<ServiceRequest>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(serviceRequests: ServiceRequest)

    @Delete
    override suspend fun delete(serviceRequests: ServiceRequest)

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    override fun getById(id: Long?): ServiceRequest

    @Update
    override suspend fun update(serviceRequests: ServiceRequest)

}