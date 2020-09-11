package com.example.android.todokotlin.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(activity: Activity){
    val inpMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    val currentFocusView = activity.currentFocus

    currentFocusView?.let{
        inpMethodManager.hideSoftInputFromWindow(
            currentFocusView.windowToken,InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}