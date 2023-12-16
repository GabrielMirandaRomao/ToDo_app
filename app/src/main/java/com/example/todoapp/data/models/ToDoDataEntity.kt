package com.example.todoapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.data.models.Priority

@Entity(tableName = "to_do_table")
data class ToDoDataEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
)