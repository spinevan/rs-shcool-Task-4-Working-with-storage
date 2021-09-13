package com.example.task4workingwithstorage.room

import android.content.ContentValues
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


    inner class serviceRequestDao(): IServiceRequestDAO {

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

            val cursor: Cursor = readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY id DESC", null)
            val resList = getListFromCursor(cursor)
            val res = MutableLiveData<List<ServiceRequest>>().apply { postValue(resList) }

            return res
        }

        override fun allServiceRequestsSortByName(): LiveData<List<ServiceRequest>> {
            Log.d(LOG_TAG, "THIS IS CURSOR METHOD allServiceRequestsSortByName!")

            val cursor: Cursor = readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY name", null)
            val resList = getListFromCursor(cursor)
            val res = MutableLiveData<List<ServiceRequest>>().apply { postValue(resList) }

            return res
        }

        override fun allServiceRequestsSortByDate(): LiveData<List<ServiceRequest>> {
            Log.d(LOG_TAG, "THIS IS CURSOR METHOD allServiceRequestsSortByDate!")

            val cursor: Cursor = readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY date_time", null)
            val resList = getListFromCursor(cursor)
            val res = MutableLiveData<List<ServiceRequest>>().apply { postValue(resList) }
            return res
        }

        override fun allServiceRequestsSortByMaster(): LiveData<List<ServiceRequest>> {
            Log.d(LOG_TAG, "THIS IS CURSOR METHOD allServiceRequestsSortByMaster!")

            val cursor: Cursor = readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY master", null)
            val resList = getListFromCursor(cursor)
            val res = MutableLiveData<List<ServiceRequest>>().apply { postValue(resList) }
            return res
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

            readableDatabase.execSQL("UPDATE $TABLE_NAME " +
                    " SET name = '${serviceRequests.name}', date_time = '${serviceRequests.dateTime?.time}', master = '${serviceRequests.master}'" +
                    " WHERE id = ${serviceRequests.id}")

        }

    }

}