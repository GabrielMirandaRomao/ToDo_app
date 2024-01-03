package com.example.todoapp.ui.update

import android.app.AlertDialog
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
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.ToDoViewModel
import com.example.todoapp.data.models.ToDoDataEntity
import com.example.todoapp.databinding.FragmentUpdateBinding
import com.example.todoapp.ui.SharedViewModel

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private val args: UpdateFragmentArgs by navArgs()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val toDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeScreen()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    private fun makeScreen() {
        binding.etCurrentTaskName.setText(args.taskInfo.title)
        binding.etCurrentDescription.setText(args.taskInfo.description)
        binding.spinnerPriority.setSelection(sharedViewModel.parsePriorityToInt(args.taskInfo.priority))
        binding.spinnerPriority.onItemSelectedListener = sharedViewModel.listener
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> deleteTask()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = binding.etCurrentTaskName.text.toString()
        val description = binding.etCurrentDescription.text.toString()

        if (sharedViewModel.checkDataFromUser(title, description)) {
            toDoViewModel.updateData(
                ToDoDataEntity(
                    args.taskInfo.id,
                    title,
                    sharedViewModel.parsePriority(binding.spinnerPriority.selectedItem.toString()),
                    description
                )
            )
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()

            findNavController().popBackStack()
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun deleteTask() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setPositiveButton("Yes") { _, _ ->
            toDoViewModel.deleteTask(args.taskInfo)

            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.taskInfo.title}",
                Toast.LENGTH_SHORT
            ).show()

            findNavController().popBackStack()
        }
        builder.setNegativeButton("No") {_,_ -> }
        builder.setTitle("Delete '${args.taskInfo.title}'?")
        builder.setMessage("Would you like to delete it?")
        builder.create().show()
    }
}