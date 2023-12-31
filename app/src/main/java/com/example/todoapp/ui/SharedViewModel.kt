package com.example.todoapp.ui

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.R
import com.example.todoapp.data.models.Priority
import com.example.todoapp.data.models.ToDoDataEntity

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val emptyDataBase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIsDataBaseEmpty(toDoDataEntity: List<ToDoDataEntity>) {
        emptyDataBase.value = toDoDataEntity.isEmpty()
    }

    val listener: AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (position) {
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.red
                        )
                    )
                }

                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.yellow
                        )
                    )
                }

                2 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.green
                        )
                    )
                }
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }

    fun checkDataFromUser(title: String, description: String): Boolean =
        title.isNotEmpty() && description.isNotEmpty()

    fun parsePriority(priority: String): Priority =
        when (priority) {
            "High Priority" -> {
                Priority.HIGH
            }

            "Medium Priority" -> {
                Priority.MEDIUM
            }

            "Low Priority" -> {
                Priority.LOW
            }

            else -> {
                Priority.LOW
            }
        }


    fun parsePriorityToInt(priority: Priority) =
        when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
}

