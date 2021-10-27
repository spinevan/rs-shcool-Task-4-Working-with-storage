package com.example.task4workingwithstorage.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "service_requests_table")
data class ServiceRequest(
    @PrimaryKey
    var id:Long?,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "date_time")
    var dateTime: Date? = null,

    @ColumnInfo(name = "master")
    var master: String? = null
)
