package com.example.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.example.todoapp.data.ToDoDao
import com.example.todoapp.data.models.ToDoDataEntity

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoDataEntity>> = toDoDao.getAllData()

    suspend fun insertData(todDoDataEntity: ToDoDataEntity) {
        toDoDao.insertData(todDoDataEntity)
    }

}