package com.example.android.todokotlin.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.todokotlin.R
import com.example.android.todokotlin.data.model.ToDoData
import com.example.android.todokotlin.data.viewmodel.ToDoViewModel
import com.example.android.todokotlin.databinding.FragmentListBinding
import com.example.android.todokotlin.databinding.FragmentUpdateBinding
import com.example.android.todokotlin.fragments.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.spinnerPrioritiesLabel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by viewModels()
    private val toDoViewModel: ToDoViewModel by viewModels()
    private  val args by navArgs<UpdateFragmentArgs>()

    private var _binding: FragmentUpdateBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment with data binding
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args


        // Set up menu - do not forget to override the onCreateOptionMenu and onOptionsItemSelected
        setHasOptionsMenu(true)

//        binding.editTextUpdateTitle.setText(args.itemToBeEdit.title)
//        binding.editTextTextMultiLineUpdateDescription.setText(args.itemToBeEdit.description)
//        binding.spinnerUpdatePrioritiesLabel.setSelection(sharedViewModel.parsePriorityToIntPos(args.itemToBeEdit.priority))

        binding.spinnerUpdatePrioritiesLabel.onItemSelectedListener = sharedViewModel.spinnerListener

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_update_fragment,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemSaveUpdate -> updateItem()
            R.id.itemDeleteUpdate -> deleteItem()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteItem() {
        val builder = AlertDialog.Builder(requireContext())
        //set title for alert dialog
        builder.setTitle(R.string.dialogTitle)
        //set message for alert dialog
        builder.setMessage(R.string.dialogMessage)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton(R.string.dialogYesOption){ _, _ ->
            toDoViewModel.deleteData(args.itemToBeEdit)
            Snackbar.make(requireView(),"Task ${args.itemToBeEdit.title} is deleted",Snackbar.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        //performing cancel action
        builder.setNeutralButton(R.string.dialogYesOption){ _, _ ->
            Snackbar.make(requireView(),"Update is cancel",Snackbar.LENGTH_SHORT).show()
        }
        //performing negative action
        builder.setNegativeButton(R.string.dialogNoOption){ _, _ ->
            Snackbar.make(requireView(),"Update is cancel",Snackbar.LENGTH_SHORT).show()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun updateItem() {
        val title = editTextUpdateTitle.text.toString()
        val description = editTextTextMultiLineUpdateDescription.text.toString()
        val priority = binding.spinnerUpdatePrioritiesLabel.selectedItem.toString()

        if(sharedViewModel.verifyToDoData(title,description)){
            // because we are updating do not forget to use the same ID
            val newUpdatedToDoItem = ToDoData(
                args.itemToBeEdit.id,
                title,
                sharedViewModel.prioritySelectionToPriority(priority),
                description
            )
            toDoViewModel.updateData(newUpdatedToDoItem)
            Toast.makeText(requireContext(),"Successfully update.",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Snackbar.make(requireView(),"Please fill in all empty fields.",Snackbar.LENGTH_SHORT)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // avoid memory leak
        _binding = null
    }
}

