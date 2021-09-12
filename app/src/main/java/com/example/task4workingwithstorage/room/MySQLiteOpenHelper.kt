package com.example.task4workingwithstorage.room

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.task4workingwithstorage.DATABASE_NAME
import com.example.task4workingwithstorage.DATABASE_VERSION
import com.example.task4workingwithstorage.LOG_TAG
import com.example.task4workingwithstorage.TABLE_NAME
import com.example.task4workingwithstorage.interfaces.IServiceRequestDAO
import com.example.task4workingwithstorage.models.ServiceRequest
import java.util.*


class MySQLiteOpenHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    companion object {
        private const val CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (id REAL PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), dateTime REAL, master VARCHAR(50) );"

    }

    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(CREATE_TABLE_SQL)
        } catch (exception: SQLException) {
            Log.e(LOG_TAG, "Exception while trying to create database", exception)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(LOG_TAG, "onUpgrade called")
    }

    fun getCursorWithServiceRequests(): Cursor {
        return readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun getListOfTopics(): List<String> {
        val listOfTopics = mutableListOf<String>()
        getCursorWithServiceRequests().use { cursor ->
            if (cursor.moveToFirst()) {
                do {
//                    val topicName = cursor.getString(cursor.getColumnIndex(TOPIC_COLUMN))
//                    listOfTopics.add("From list: $topicName")
                } while (cursor.moveToNext())
            }
        }
        return listOfTopics
    }

    inner class serviceRequestDao(): IServiceRequestDAO {
        override fun allServiceRequests(): LiveData<List<ServiceRequest>> {
            Log.d(LOG_TAG, "THIS IS CURSOR METHOD!")

            val testList = listOf<ServiceRequest>( ServiceRequest(12L, "1234", Date(), "4321") )
            val res = MutableLiveData<List<ServiceRequest>>().apply { postValue(testList) }
            return res
        }

        override fun allServiceRequestsSortByName(): LiveData<List<ServiceRequest>> {
            Log.d(LOG_TAG, "THIS IS CURSOR METHOD!")

            val testList = listOf<ServiceRequest>( ServiceRequest(12L, "1234", Date(), "4321") )
            val res = MutableLiveData<List<ServiceRequest>>().apply { postValue(testList) }
            return res
        }

        override fun allServiceRequestsSortByDate(): LiveData<List<ServiceRequest>> {
            Log.d(LOG_TAG, "THIS IS CURSOR METHOD!")

            val testList = listOf<ServiceRequest>( ServiceRequest(12L, "1234", Date(), "4321") )
            val res = MutableLiveData<List<ServiceRequest>>().apply { postValue(testList) }
            return res
        }

        override fun allServiceRequestsSortByMaster(): LiveData<List<ServiceRequest>> {
            Log.d(LOG_TAG, "THIS IS CURSOR METHOD!")

            val testList = listOf<ServiceRequest>( ServiceRequest(12L, "1234", Date(), "4321") )
            val res = MutableLiveData<List<ServiceRequest>>().apply { postValue(testList) }
            return res
        }

        override suspend fun insert(serviceRequests: ServiceRequest) {

            Log.d(LOG_TAG, "THIS IS CURSOR METHOD!")

        }

        override suspend fun delete(serviceRequests: ServiceRequest) {
            Log.d(LOG_TAG, "THIS IS CURSOR METHOD!")

        }

        override fun getById(id: Long?): ServiceRequest {

            Log.d(LOG_TAG, "THIS IS CURSOR METHOD!")

            return  ServiceRequest(12L, "1234", Date(), "4321")
        }

        override suspend fun update(serviceRequests: ServiceRequest) {

            Log.d(LOG_TAG, "THIS IS CURSOR METHOD!")

        }

    }

}