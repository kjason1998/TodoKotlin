package com.example.android.todokotlin.fragments.add

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.android.todokotlin.R
import com.example.android.todokotlin.data.model.Priority
import com.example.android.todokotlin.data.model.ToDoData
import com.example.android.todokotlin.data.viewmodel.ToDoViewModel
import com.example.android.todokotlin.fragments.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {
    /**
     * by keyword can be use for 2 things
     * 1. Delegation
     * https://proandroiddev.com/delegation-in-kotlin-e1efb849641
     * 2.
     */
    private val toDoViewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        // Set up menu - do not forget to override the onCreateOptionMenu and onOptionsItemSelected
        setHasOptionsMenu(true)

        view.spinnerPrioritiesLabel.onItemSelectedListener = sharedViewModel.spinnerListener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_fragment,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.itemAddTodo){
             addDataToDoToDatabase()
        }

        return super.onOptionsItemSelected(item)
    }

    // add new Todoitem to database
    private fun addDataToDoToDatabase() {
        val title = editTextTitle.text.toString()
        val priorityString = spinnerPrioritiesLabel.selectedItem.toString()
        val description = editTextTextMultiLineDescription.text.toString()

        // if data is (title and description is not empty)
        if(sharedViewModel.verifyToDoData(title,description)){
                // insert the data to database
                val newData = ToDoData(
                    0,
                    title,
                    sharedViewModel.prioritySelectionToPriority(priorityString),
                    description
                )
                toDoViewModel.insertData(newData)
                Snackbar.make(requireView(), "Item is added!", Snackbar.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Snackbar.make(requireView(),"Please fill in all empty fields.",Snackbar.LENGTH_SHORT).show()
        }
    }

}