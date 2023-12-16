package com.example.todoapp.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.ToDoViewModel
import com.example.todoapp.data.models.Priority
import com.example.todoapp.data.models.ToDoDataEntity
import com.example.todoapp.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val viewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val taskTitle = binding.etTaskName.text.toString()
        val priority = binding.spinnerPriority.selectedItem.toString()
        val description = binding.etDescription.text.toString()

        val validation = checkDataFromUser(taskTitle, description)

        if (validation) {
            val newData = ToDoDataEntity(
                0,
                taskTitle,
                parsePriority(priority),
                description
            )
            viewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Error while trying to add!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkDataFromUser(title: String, descriptione: String): Boolean =
        title.isNotEmpty() && descriptione.isNotEmpty()

    private fun parsePriority(priority: String): Priority {
        return when (priority) {
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
    }

}