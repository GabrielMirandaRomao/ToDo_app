package com.example.todoapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.data.models.Priority
import kotlinx.parcelize.Parcelize

@Entity(tableName = "to_do_table")
@Parcelize
data class ToDoDataEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
): Parcelable