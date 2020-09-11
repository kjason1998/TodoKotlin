package com.example.android.todokotlin.data

import android.content.Context
import androidx.room.*
import com.example.android.todokotlin.data.model.ToDoData

// change the version when changing the database scheme
@Database(entities = [ToDoData::class], version = 1)
@TypeConverters(Converter::class)
abstract class ToDoDatabase:RoomDatabase() {

    // do not be confused, it is just a function called ToDoDao that return ToDoDao
    abstract fun toDoDao(): ToDoDao


    // companion object is like public static final class (it can be access the function inside directly by other classes)
    companion object{

        // volatile means that writes in this field are immediately visible to other threads
        @Volatile
        private var INSTANCE: ToDoDatabase? = null // nullable ToDoDatabase type called instance and it is null

        fun getDatabase(context: Context): ToDoDatabase{
             val tempInstance = INSTANCE
            // check if tempInstance is already set - by checking if it not null
            if(tempInstance != null){
                return tempInstance
            }

            // we put this in synchronised block so there is no way this is called in the same time
            // and resulting to have 2 instances - that is why we also check if instance is already have value
            synchronized(this){
                // 3 parameters , application context, tododatabase class, and title/name of the database
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "ToDo_Database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }

}