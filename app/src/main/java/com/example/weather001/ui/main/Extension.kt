package com.example.weather001.ui.main

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import java.lang.RuntimeException
import java.util.Date

fun String.isContainsA() {
    this.contains("a")
}

fun Date.yesterdayTime(): Long {
    return this.time - 24*60*60*1000
}

class Extensions() {
    fun yesterdayTime(date: Date): Long {
        return date.time - 24*60*60*1000
    }
}

fun EditText.showKeyboard(){
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
            InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun EditText.hideKeyboard(): Boolean{
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return  inputMethodManager.hideSoftInputFromWindow(windowToken,0)
    } catch (ignored: RuntimeException){}
    return false
}
fun View.showSnackBar(
    text: String,
    actionText: String,
    length: Int = Snackbar.LENGTH_INDEFINITE,
    action: (View) -> Unit,
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}
