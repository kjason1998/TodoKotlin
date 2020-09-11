package com.example.android.todokotlin.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.todokotlin.data.model.ToDoData

@Dao
interface ToDoDao {
    // todo_table is the name that is set in tododata
    @Query("SELECT * FROM Todo_Table ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDoData>>

    @Query("SELECT * FROM Todo_Table WHERE title LIKE :string")
    fun searchTitle(string: String): LiveData<List<ToDoData>>

    @Query("SELECT * FROM Todo_Table ORDER BY CASE WHEN priority LIKE 'W%' THEN 1 WHEN priority LIKE 'D%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun getDataSortByHighPriority(): LiveData<List<ToDoData>>

    @Query("SELECT * FROM Todo_Table ORDER BY CASE WHEN priority LIKE 'W%' THEN 3 WHEN priority LIKE 'D%' THEN 2 WHEN priority LIKE 'L%' THEN 1  END")
    fun getDataSortByLowPriority(): LiveData<List<ToDoData>>

    @Query("DELETE FROM Todo_Table")
    fun deleteAllData()

    // ignore if there is a same data trying to be insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData) // we use suspend in front of the function so that our function will run in background thread in the view model (inside the coroutines)

    @Update
    suspend fun updateData(toDoData: ToDoData)

    @Delete
    suspend fun deleteData(toDoData: ToDoData)
}
