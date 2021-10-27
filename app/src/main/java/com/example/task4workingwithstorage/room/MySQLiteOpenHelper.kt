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
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, date_time INTEGER, master TEXT );"
    }

    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(CREATE_TABLE_SQL)

            (1..6).forEach {
                db.execSQL("INSERT INTO $TABLE_NAME (name, date_time, master) " +
                        " VALUES ('Клиент$it', '${Date().time}}', 'Мастер$it');")
            }

        } catch (exception: SQLException) {
            Log.e(LOG_TAG, "Exception while trying to create database", exception)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(LOG_TAG, "onUpgrade called")
    }

    inner class ServiceRequestDao(): IServiceRequestDAO {

        private fun getListFromCursor(cursor: Cursor): List<ServiceRequest> {

            val result = mutableListOf<ServiceRequest>()

            cursor.use { it ->
                if (it.moveToFirst()) {
                    do {
                        val id = it.getLong(it.getColumnIndexOrThrow("id"))
                        val name: String = it.getString(it.getColumnIndexOrThrow("name"))
                        val date = it.getLong(it.getColumnIndexOrThrow("date_time"))
                        val master: String = it.getString(it.getColumnIndexOrThrow("master"))

                        result.add( ServiceRequest(id, name, Date(date), master) )

                    } while (it.moveToNext())
                }
            }

            return result.toList()
        }

        override fun allServiceRequests(): LiveData<List<ServiceRequest>> {
            Log.d(LOG_TAG, "THIS IS CURSOR METHOD allServiceRequests!")
            return allServiceRequestsFromDB("SELECT * FROM $TABLE_NAME ORDER BY id DESC")
        }

        override fun allServiceRequestsSortByName(): LiveData<List<ServiceRequest>> {
            Log.d(LOG_TAG, "THIS IS CURSOR METHOD allServiceRequestsSortByName!")
            return allServiceRequestsFromDB("SELECT * FROM $TABLE_NAME ORDER BY name")
        }

        override fun allServiceRequestsSortByDate(): LiveData<List<ServiceRequest>> {
            Log.d(LOG_TAG, "THIS IS CURSOR METHOD allServiceRequestsSortByDate!")
            return allServiceRequestsFromDB("SELECT * FROM $TABLE_NAME ORDER BY date_time")
        }

        override fun allServiceRequestsSortByMaster(): LiveData<List<ServiceRequest>> {
            Log.d(LOG_TAG, "THIS IS CURSOR METHOD allServiceRequestsSortByMaster!")
            return allServiceRequestsFromDB("SELECT * FROM $TABLE_NAME ORDER BY master")
        }

        private fun allServiceRequestsFromDB(query: String): LiveData<List<ServiceRequest>> {
            val cursor: Cursor = readableDatabase.rawQuery(query, null)
            val resList = getListFromCursor(cursor)
            return MutableLiveData<List<ServiceRequest>>().apply { postValue(resList) }
        }

        override suspend fun insert(serviceRequests: ServiceRequest) {
            Log.d(LOG_TAG, "THIS IS CURSOR METHOD insert!")

            readableDatabase.execSQL("INSERT INTO $TABLE_NAME (name, date_time, master) " +
                    " VALUES ('${serviceRequests.name}', '${serviceRequests.dateTime?.time}', '${serviceRequests.master}');")

        }

        override suspend fun delete(serviceRequests: ServiceRequest) {
            Log.d(LOG_TAG, "THIS IS CURSOR METHOD delete!")

            readableDatabase.delete(TABLE_NAME, "id = ${serviceRequests.id}", null)

        }

        override fun getById(id: Long?): ServiceRequest {

            Log.d(LOG_TAG, "THIS IS CURSOR METHOD getById!")

            val cursor: Cursor = readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME WHERE id = $id", null)

            val serviceRequest = ServiceRequest(null, "", Date(), "")

            cursor.use { it ->
                if (it.moveToFirst()) {
                    do {
                        serviceRequest.id = it.getLong(it.getColumnIndexOrThrow("id"))
                        serviceRequest.name = it.getString(it.getColumnIndexOrThrow("name"))
                        serviceRequest.dateTime =  Date(it.getLong(it.getColumnIndexOrThrow("date_time")))
                        serviceRequest.master = it.getString(it.getColumnIndexOrThrow("master"))
                    } while (it.moveToNext())
                }
            }

            return serviceRequest

        }

        override suspend fun update(serviceRequests: ServiceRequest) {
            Log.d(LOG_TAG, "THIS IS CURSOR METHOD update!")

            readableDatabase.execSQL(""" 
                UPDATE $TABLE_NAME
                | SET name = '${serviceRequests.name}', date_time = '${serviceRequests.dateTime?.time}', master = '${serviceRequests.master}'
                |   WHERE id = ${serviceRequests.id}""".trimMargin())

        }
    }
}