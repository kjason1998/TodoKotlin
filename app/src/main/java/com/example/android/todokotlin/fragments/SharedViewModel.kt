package com.example.android.todokotlin.fragments

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.android.todokotlin.R
import com.example.android.todokotlin.data.model.Priority
import com.example.android.todokotlin.data.model.ToDoData

class SharedViewModel(application: Application): AndroidViewModel(application) {

    // the default value is false because sometimes it
    // will shown the no data image when there is data
    val isEmptyDatabase :MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfEmptyToDoDatabase(toDoData: List<ToDoData>){
        isEmptyDatabase.value = toDoData.isEmpty()
    }


    val spinnerListener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position){
                0 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,R.color.red))}
                1 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,R.color.yellow))}
                2 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,R.color.green))}
            }
        }
    }

    /*
    check if title and description is not empty
    if both not empty return true otherwise false
     */
    fun verifyToDoData(title: String,description: String): Boolean{
        return (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description) )
    }

    fun prioritySelectionToPriority(priorityString: String): Priority{
       return when (priorityString){
            "Work task" -> Priority.WORK_TASK
            "Daily task" -> Priority.DAILY_TASK
            "Leisure task" -> Priority.LEISURE_TASK
           else -> Priority.ERR_TASK
       }
    }
}