package br.edu.infnet.meusgastos.utils

import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.util.Date

fun Fragment.nav(id: Int){
    findNavController().navigate(id)
}

fun Fragment.navUp(){
    findNavController().navigateUp()
}

fun Fragment.getTextInput(editText: EditText): String {
    return editText.text.toString()
}


fun Fragment.getIntInput(editText: EditText): Int {
    return editText.text.toString().toInt()
}
fun Fragment.getFloatInput(editText: EditText): Float {
    return editText.text.toString().toFloat()
}

fun Fragment.getDoubleInput(editText: EditText): Double {
    return editText.text.toString().toDouble()
}


fun Fragment.toast(msg: String){
    Toast.makeText(
        requireContext(),
        msg,
        Toast.LENGTH_SHORT
    ).show()
}