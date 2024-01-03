package com.example.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.example.todoapp.data.ToDoDao
import com.example.todoapp.data.models.ToDoDataEntity

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoDataEntity>> = toDoDao.getAllData()
    val sortByHighPriority: LiveData<List<ToDoDataEntity>> = toDoDao.sortByHighPriority()
    val sortByLowPriority: LiveData<List<ToDoDataEntity>> = toDoDao.sortByLowPriority()

    suspend fun insertData(todDoDataEntity: ToDoDataEntity) {
        toDoDao.insertData(todDoDataEntity)
    }

    suspend fun updateData(todDoDataEntity: ToDoDataEntity) {
        toDoDao.updateData(todDoDataEntity)
    }

    suspend fun deleteData(todDoDataEntity: ToDoDataEntity) {
        toDoDao.deleteData(todDoDataEntity)
    }

    suspend fun deleteAllData() {
        toDoDao.deleteAllData()
    }

    fun searchDataBase(searchQuery: String): LiveData<List<ToDoDataEntity>> =
        toDoDao.searchDataBase(searchQuery)
}