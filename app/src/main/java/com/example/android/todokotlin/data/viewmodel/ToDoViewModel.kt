package com.example.android.todokotlin.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.android.todokotlin.data.ToDoDatabase
import com.example.android.todokotlin.data.model.ToDoData
import com.example.android.todokotlin.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//View model
//
//so a view model role is to provide that
//data to the UI and the survivor configuration changes view model acts as a communication center between
//the repository and the UI.
class ToDoViewModel(application: Application): AndroidViewModel(application) {

    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private val repository: ToDoRepository
    val getAllData: LiveData<List<ToDoData>>
    val getDataSortByHighPriority: LiveData<List<ToDoData>>
    val getDataSortByLowPriority: LiveData<List<ToDoData>>

    // init block will be called every time todoviewmodel is initialised
    init {
        repository = ToDoRepository(toDoDao)
        getAllData = repository.getAllData
        getDataSortByHighPriority = repository.getDataSortByHighPriority
        getDataSortByLowPriority = repository.getDataSortByLowPriority
    }

    fun searchTitle(string: String): LiveData<List<ToDoData>>{
        return repository.searchTitle(string)
    }

    fun insertData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO ) {
            repository.insertData(toDoData)
        }
    }

    fun updateData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO ) {
            repository.updateData(toDoData)
        }
    }

    fun deleteData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO ) {
            repository.deleteData(toDoData)
        }
    }

    fun deleteAllData(){
        viewModelScope.launch(Dispatchers.IO ) {
            repository.deleteAllData()
        }
    }
}