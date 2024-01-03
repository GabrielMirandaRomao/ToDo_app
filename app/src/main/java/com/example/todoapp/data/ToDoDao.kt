package com.example.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.models.ToDoDataEntity

@Dao
interface ToDoDao {

    @Query("SELECT * FROM to_do_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDoDataEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoDataEntity: ToDoDataEntity)

    @Update
    suspend fun updateData(toDoDataEntity: ToDoDataEntity)

    @Delete
    suspend fun deleteData(toDoDataEntity: ToDoDataEntity)

    @Query("DELETE FROM to_do_table")
    suspend fun deleteAllData()
    @Query("SELECT * FROM to_do_table WHERE title LIKE :searchQuery")
    fun searchDataBase(searchQuery: String): LiveData<List<ToDoDataEntity>>

    @Query("SELECT * FROM to_do_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<ToDoDataEntity>>

    @Query("SELECT * FROM to_do_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<ToDoDataEntity>>
}