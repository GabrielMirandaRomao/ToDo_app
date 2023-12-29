package com.example.todoapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setListener()
        setupRecyclerView()
    }

    private fun setListener() {
        binding.fbtnAddTasks.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
    }
    private fun setObserver(){
        viewModel.getAllData.observe(viewLifecycleOwner) { toDoList ->
            adapter.updateTaskList(toDoList)
        }
    }

    private fun setupRecyclerView() {
        binding.rvTasksList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTasksList.adapter = adapter
    }
}