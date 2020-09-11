package com.example.android.todokotlin.data.repository

import androidx.lifecycle.LiveData
import com.example.android.todokotlin.data.ToDoDao
import com.example.android.todokotlin.data.model.ToDoData

// it will take ToDoDao interface as a parameter to get the reference
class ToDoRepository(private val toDoDao: ToDoDao) {

    // this is similar with our tododao
    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    val getDataSortByHighPriority: LiveData<List<ToDoData>> = toDoDao.getDataSortByHighPriority()

    val getDataSortByLowPriority: LiveData<List<ToDoData>> = toDoDao.getDataSortByLowPriority()

    fun searchTitle(string: String):LiveData<List<ToDoData>>{
        return toDoDao.searchTitle("%$string%")
    }

    suspend fun insertData(toDoData: ToDoData){
        toDoDao.insertData(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData){
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteData(toDoData: ToDoData){
        toDoDao.deleteData(toDoData)
    }

    suspend fun deleteAllData(){
        toDoDao.deleteAllData()
    }
}