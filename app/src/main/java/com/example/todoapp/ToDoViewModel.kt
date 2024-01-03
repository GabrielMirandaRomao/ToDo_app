package com.example.todoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.ToDoDatabase
import com.example.todoapp.data.models.ToDoDataEntity
import com.example.todoapp.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private val repository: ToDoRepository = ToDoRepository(toDoDao)

    val getAllData: LiveData<List<ToDoDataEntity>>
    val sortByHighPriority: LiveData<List<ToDoDataEntity>>
    val sortByLowPriority: LiveData<List<ToDoDataEntity>>

    init {
        repository
        getAllData = repository.getAllData
        sortByHighPriority = repository.sortByHighPriority
        sortByLowPriority = repository.sortByLowPriority
    }

    fun insertData(toDoDataEntity: ToDoDataEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoDataEntity)
        }
    }

    fun updateData(toDoDataEntity: ToDoDataEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(toDoDataEntity)
        }
    }

    fun deleteTask(toDoDataEntity: ToDoDataEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(toDoDataEntity)
        }
    }

    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }

    fun searchDataBase(searchQuery: String): LiveData<List<ToDoDataEntity>> =
        repository.searchDataBase(searchQuery)
}