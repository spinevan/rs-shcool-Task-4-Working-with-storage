package com.example.task4workingwithstorage.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.task4workingwithstorage.DATABASE_NAME
import com.example.task4workingwithstorage.dao.ServiceRequestDAO
import com.example.task4workingwithstorage.models.ServiceRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

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
                    .addCallback( BDCallback() )
                    .build()
            }

            return INSTANCE as RoomSingleton
        }
    }

    private class BDCallback() : RoomDatabase.Callback() {
        /**
         * Override the onCreate method to populate the database.
         */
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // If you want to keep the data through app restarts,
            // comment out the following line.
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch() {
                    populateDatabase(database.serviceRequestDao())
                }
            }
        }

        suspend fun populateDatabase(serviceRequestDao: ServiceRequestDAO) {

            (1..6).forEach {
                val item = ServiceRequest(
                    null,
                    "Клиент$it",
                    Date(),
                    "Мастер$it")
                serviceRequestDao.insert(item)
            }
        }
    }
}