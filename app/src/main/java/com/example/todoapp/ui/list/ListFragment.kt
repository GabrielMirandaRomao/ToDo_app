package com.example.todoapp.ui.list

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.ToDoViewModel
import com.example.todoapp.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val viewModel: ToDoViewModel by viewModels()
    private val adapter: TaskListAdapter by lazy { TaskListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setListener()
        setupRecyclerView()
        viewModel.checkListStatus()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> deleteAllTask()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllTask() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteAllData()
            viewModel.checkListStatus()
            Toast.makeText(
                requireContext(),
                "Successfully removed all data.",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete all data?")
        builder.setMessage("Would you like to delete it?")
        builder.create().show()
    }

    private fun setListener() {
        binding.fbtnAddTasks.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
    }

    private fun setObserver() {
        viewModel.getAllData.observe(viewLifecycleOwner) { toDoList ->
            adapter.updateTaskList(toDoList)
        }

        viewModel.toDoListStatus.observe(viewLifecycleOwner) { isListEmpty ->
            if (isListEmpty) {
                binding.ivEmptyList.visibility = View.GONE
                binding.tvMessage.visibility = View.GONE
            } else {
                binding.ivEmptyList.visibility = View.VISIBLE
                binding.tvMessage.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvTasksList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTasksList.adapter = adapter
    }
}