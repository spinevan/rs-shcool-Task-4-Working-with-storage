package com.example.task4workingwithstorage.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}

//public class OffsetDateTimeConverter {
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    @TypeConverter
//    fun toOffsetDateTime(value: String?): OffsetDateTime? {
//        return value?.let {
//            return formatter.parse(value, OffsetDateTime::from)
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    @TypeConverter
//    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
//        return date?.format(formatter)
//    }
//
////    @RequiresApi(Build.VERSION_CODES.O)
////    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
////
////    @TypeConverter
////    @JvmStatic
////    fun toOffsetDateTime(value: String?): OffsetDateTime? {
////        return value?.let {
////            return formatter.parse(value, OffsetDateTime::from)
////        }
////    }
////
////    @TypeConverter
////    @JvmStatic
////    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
////        return date?.format(formatter)
////    }
//}



