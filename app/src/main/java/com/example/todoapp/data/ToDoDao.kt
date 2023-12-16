package com.example.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todoapp.data.models.ToDoDataEntity

@Dao
interface ToDoDao {

    @Query("SELECT * FROM to_do_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDoDataEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoDataEntity: ToDoDataEntity)
}