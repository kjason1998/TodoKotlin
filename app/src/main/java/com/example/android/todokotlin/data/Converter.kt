package com.example.android.todokotlin.data

import android.util.Log
import androidx.room.TypeConverter
import com.example.android.todokotlin.data.model.Priority

// this class is to convert data that are non_primitive to primitive so that it can be
// store and access in Romm database
class Converter {

    @TypeConverter
    fun convertPriority2String(priority: Priority): String{
        return priority.name
    }

    @TypeConverter
    fun convertString2Priority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

}