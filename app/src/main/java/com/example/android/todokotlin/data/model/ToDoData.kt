package com.example.android.todokotlin.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


/**
 * parcelize anotation and extend parcelable to make sure it recognise by safe args
 * about safe args is like passing data
 * If you need to pass large amounts of data,
 * consider using a ViewModel as described in Share data between fragments.
 */
@Entity (tableName = "Todo_Table")
@Parcelize
data class ToDoData (
    @PrimaryKey(autoGenerate = true )
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
): Parcelable