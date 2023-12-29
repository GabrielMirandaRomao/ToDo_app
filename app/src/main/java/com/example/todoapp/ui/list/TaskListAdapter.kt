package com.example.todoapp.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.models.Priority
import com.example.todoapp.data.models.ToDoDataEntity
import com.example.todoapp.databinding.TaskItemBinding

class TaskListAdapter() : RecyclerView.Adapter<TaskListAdapter.NewTaskViewHolder>() {

    var taskList = emptyList<ToDoDataEntity>()

    class NewTaskViewHolder(val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewTaskViewHolder {
        val item = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewTaskViewHolder(item)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: NewTaskViewHolder, position: Int) {

        holder.binding.apply {
            tvTaskTitle.text = taskList[position].title
            tvTaskDescription.text = taskList[position].description

            when (taskList[position].priority) {
                Priority.HIGH -> cvPriorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.red
                    )
                )

                Priority.MEDIUM -> cvPriorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.yellow
                    )
                )

                Priority.LOW -> cvPriorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.green
                    )
                )
            }
        }

        holder.itemView.setOnClickListener {
            val action =
                ListFragmentDirections.actionListFragmentToUpdateFragment(taskList[position])
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun updateTaskList(listToDO: List<ToDoDataEntity>) {
        this.taskList = listToDO
        notifyDataSetChanged()
    }
}