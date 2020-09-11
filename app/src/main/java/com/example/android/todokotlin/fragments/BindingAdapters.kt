package com.example.android.todokotlin.fragments

import android.graphics.drawable.GradientDrawable
import android.renderscript.RenderScript
import android.view.View
import android.widget.ImageView
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.android.todokotlin.R
import com.example.android.todokotlin.data.model.Priority
import com.example.android.todokotlin.data.model.ToDoData
import com.example.android.todokotlin.fragments.list.ListFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapters {
    // companion object - is like public static class (everyone can access this object?)
    companion object{
        /*
        to navigate from list to add fragment
        inside the bindingadapter annotation is the attribute
        note: this is why we do not on click listener in our ListFragment.kt
        also do not forget to change the layout file to data biding layout and use the method on the
        button.
         */
        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(addButton: FloatingActionButton,navigate: Boolean){
            addButton.setOnClickListener {
                if(navigate){
                    addButton.findNavController().navigate(R.id.action_listFragment_to_addFragment )
                }
            }
        }

        @BindingAdapter("android:isEmptyToDoTask")
        @JvmStatic
        fun emptyDatabase(view: View, isDatabaseEmpty: MutableLiveData<Boolean>){
            when(isDatabaseEmpty.value){
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        /**
         * This is used in list fragment in the image view to indicate the priority using color
         */
        @BindingAdapter("android:priorityImageView")
        @JvmStatic
        fun priorityImageView(imageViewPriority: ImageView, priority: Priority){
            when (priority) {
            Priority.DAILY_TASK -> (imageViewPriority.background as GradientDrawable)
                .setColor(ResourcesCompat.getColor(imageViewPriority.resources, R.color.yellow, null))

            Priority.LEISURE_TASK -> (imageViewPriority.background as GradientDrawable)
                .setColor(ResourcesCompat.getColor(imageViewPriority.resources, R.color.green, null))

            Priority.WORK_TASK -> (imageViewPriority.background as GradientDrawable)
                .setColor(ResourcesCompat.getColor(imageViewPriority.resources, R.color.red, null))

            Priority.ERR_TASK -> (imageViewPriority.background as GradientDrawable)
                .setColor(ResourcesCompat.getColor(imageViewPriority.resources, R.color.red, null))
            }
        }

        @BindingAdapter("android:onClickToUpdateFragment")
        @JvmStatic
        fun onClickToUpdateFragment(view: ConstraintLayout,toDoData: ToDoData){
            view.setOnClickListener{
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(toDoData)
                view.findNavController().navigate(action)
            }
        }


        /**
         * this is used in update fragment to pass the current priority value of the todotask
         */
        @BindingAdapter("android:spinnerSelectPriorityToIntPosition")
        @JvmStatic
        fun spinnerSelectPriorityToIntPosition(spinner: Spinner, priority: Priority){
            when(priority){
                Priority.WORK_TASK -> spinner.setSelection(0)
                Priority.DAILY_TASK -> spinner.setSelection(1)
                Priority.LEISURE_TASK -> spinner.setSelection(2)
                else -> spinner.setSelection(0)
            }
        }
    }
}