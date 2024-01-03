package com.example.todoapp.ui.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todoapp.R
import com.example.todoapp.ToDoViewModel
import com.example.todoapp.data.models.ToDoDataEntity
import com.example.todoapp.databinding.FragmentListBinding
import com.example.todoapp.ui.SharedViewModel
import com.example.todoapp.ui.list.adapter.TaskListAdapter
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentListBinding
    private val viewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
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
    }

    private fun setListener() {
        binding.fbtnAddTasks.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
    }

    private fun setObserver() {
        viewModel.getAllData.observe(viewLifecycleOwner) { toDoList ->
            adapter.updateTaskList(toDoList)
            sharedViewModel.checkIsDataBaseEmpty(toDoList)
        }

        sharedViewModel.emptyDataBase.observe(viewLifecycleOwner) { isListEmpty ->
            if (isListEmpty) {
                binding.ivEmptyList.visibility = View.VISIBLE
                binding.tvMessage.visibility = View.VISIBLE
            } else {
                binding.ivEmptyList.visibility = View.GONE
                binding.tvMessage.visibility = View.GONE
            }
        }
    }

    private fun setupRecyclerView() {
        val rvTasksList = binding.rvTasksList
        rvTasksList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvTasksList.adapter = adapter
        rvTasksList.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 500
        }

        swipeToDelete(rvTasksList)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.taskList[viewHolder.adapterPosition]
                viewModel.deleteTask(deletedItem)
                adapter.notifyItemChanged(viewHolder.adapterPosition)

                restoreDeleteData(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeleteData(view: View, deletedItem: ToDoDataEntity, position: Int) {
        val snackBar = Snackbar.make(view, "Deleted: '${deletedItem.title}'", Snackbar.LENGTH_SHORT)
        snackBar.setAction("Undo") {
            viewModel.insertData(deletedItem)
            adapter.notifyItemChanged(position)
        }
        snackBar.show()
    }

    private fun searchThroughDataBase(query: String) {
        viewModel.searchDataBase("%$query%").observe(this) { list ->
            list?.let {
                adapter.updateTaskList(list)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView

        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> deleteAllTask()
            R.id.menu_priority_high -> viewModel.sortByHighPriority.observe(this) {
                adapter.updateTaskList(it)
            }

            R.id.menu_priority_low -> viewModel.sortByLowPriority.observe(this) {
                adapter.updateTaskList(it)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDataBase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchThroughDataBase(query)
        }
        return true
    }

    private fun deleteAllTask() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteAllData()
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
}