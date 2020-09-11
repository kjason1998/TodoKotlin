package com.example.android.todokotlin.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.android.todokotlin.R
import com.example.android.todokotlin.data.model.ToDoData
import com.example.android.todokotlin.data.viewmodel.ToDoViewModel
import com.example.android.todokotlin.fragments.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import com.example.android.todokotlin.databinding.FragmentListBinding
import com.example.android.todokotlin.fragments.list.adapter.ToDoListAdapter
import com.example.android.todokotlin.util.hideKeyboard
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    // by lazy {} -> inside the curly bracket is the initialization method, can be function like below
    // or some cod
    private val adapter: ToDoListAdapter by lazy { ToDoListAdapter() }

    private val toDoViewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // make sure if we go to list fragment from other fragments we close the keyboard.
        hideKeyboard(requireActivity())

        // Inflate the layout for this fragment with data binding
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.sharedViewModel = sharedViewModel
        binding.lifecycleOwner = this


        //setting up recycler view
        initializeRecyclerView()

        // only live data can be observe - that is why live data is to update UI mostly
        toDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            sharedViewModel.checkIfEmptyToDoDatabase(data)
            adapter.setData(data)
        })

        // we do not need to have any observe because the data binding
//        sharedViewModel.isEmptyDatabase.observe(viewLifecycleOwner, Observer {
//            emptyTaskUIUpdate(it)
//        })

        // we use data binding already so we do not need this
//        view.floatingActionButtonAddTodo.setOnClickListener {
//            findNavController().navigate(R.id.action_listFragment_to_addFragment)
//        }

        // Set up menu - do not forget to override the onCreateOptionMenu and onOptionsItemSelected
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun initializeRecyclerView() {
        val recyclerView = binding.recyclerViewListTodo
        recyclerView.adapter = ScaleInAnimationAdapter(adapter).apply {
            // Disable the first scroll mode.
            setFirstOnly(false)
        }
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        // set up the animation using custome library by https://github.com/wasabeef/recyclerview-animators
        recyclerView.itemAnimator = SlideInUpAnimator()

        // connect the swiptodelete to the items in recycler view
        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val itemToDelete = adapter.dataList[position]
                toDoViewModel.deleteData(itemToDelete)
                adapter.notifyItemRemoved(position)
                val snackBarRemoveItem = Snackbar.make(
                    requireView(),
                    "Task ${itemToDelete.title} is removed",
                    Snackbar.LENGTH_LONG
                )
                snackBarRemoveItem.setAction("Undo") { removeUndoListener(itemToDelete, position) }
                snackBarRemoveItem.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        // connect the swipe to delete to the items in recycler view
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun removeUndoListener(deletedItem: ToDoData, position: Int) {
        toDoViewModel.insertData(deletedItem)
        adapter.notifyItemChanged(position)
    }

//    private fun emptyTaskUIUpdate(taskIsEmpty: Boolean) {
//        if(taskIsEmpty){
//            imageViewNoDataIcon.visibility = View.VISIBLE
//            textViewNoData.visibility = View.VISIBLE
//        }else{
//            imageViewNoDataIcon.visibility = View.INVISIBLE
//            textViewNoData.visibility = View.INVISIBLE
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list_fragment, menu)

        //val searchBar = menu.findItem(R.id.searchBar)
        val searchBarView = menu.findItem(R.id.menu_search).actionView as? SearchView
        searchBarView?.isSubmitButtonEnabled = true
        searchBarView?.setOnQueryTextListener(this) // since we have implemented it we pass this

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> deleteAllItem()
            R.id.menu_priority_high -> toDoViewModel.getDataSortByHighPriority.observe(this, Observer { data ->
                sharedViewModel.checkIfEmptyToDoDatabase(data)
                data?.let {
                    adapter.setData(it)
                }
            })
            R.id.menu_priority_low -> toDoViewModel.getDataSortByLowPriority .observe(this, Observer { data ->
                sharedViewModel.checkIfEmptyToDoDatabase(data)
                data?.let {
                    adapter.setData(it)
                }
            })
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllItem() {
        val builder = AlertDialog.Builder(requireContext())
        //set title for alert dialog
        builder.setTitle(R.string.dialogTitleDeleteAll)
        //set message for alert dialog
        builder.setMessage(R.string.dialogMessageDeleteAll)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton(R.string.dialogYesOption) { _, _ ->
            toDoViewModel.deleteAllData()
            Snackbar.make(requireView(), "All task are deleted", Snackbar.LENGTH_SHORT).show()
        }
        //performing cancel action
        builder.setNeutralButton(R.string.dialogCancelOption) { _, _ ->
            Snackbar.make(requireView(), "Deletion is cancelled", Snackbar.LENGTH_SHORT).show()
        }
        //performing negative action
        builder.setNegativeButton(R.string.dialogNoOption) { _, _ ->
            Snackbar.make(requireView(), "Tasks are no deleted", Snackbar.LENGTH_SHORT).show()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    /**
     * do not forget to do if using data binding to avoid memory leak
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(string: String?): Boolean {
        if (string != null && !TextUtils.isEmpty(string)) {
            searchDatabase(string)
        }
        return true
    }


    override fun onQueryTextChange(string: String?): Boolean {
        if (string != null && !TextUtils.isEmpty(string)) {
            searchDatabase(string)
        }
        return true
    }

    private fun searchDatabase(string: String) {
        toDoViewModel.searchTitle(string).observe(this, Observer { data ->
            sharedViewModel.checkIfEmptyToDoDatabase(data)
            data?.let {
                adapter.setData(it)
            }
        })
    }
}