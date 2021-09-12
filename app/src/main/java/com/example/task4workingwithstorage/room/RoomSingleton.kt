package com.example.task4workingwithstorage.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.task4workingwithstorage.DATABASE_NAME
import com.example.task4workingwithstorage.dao.ServiceRequestDAO
import com.example.task4workingwithstorage.models.ServiceRequest

@Database(entities = arrayOf(ServiceRequest::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RoomSingleton : RoomDatabase(){
    abstract fun serviceRequestDao(): ServiceRequestDAO

    companion object{
        private var INSTANCE: RoomSingleton? = null
        fun getInstance(context: Context): RoomSingleton{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    RoomSingleton::class.java,
                    DATABASE_NAME
                )
                    .build()
            }

            return INSTANCE as RoomSingleton
        }
    }
}