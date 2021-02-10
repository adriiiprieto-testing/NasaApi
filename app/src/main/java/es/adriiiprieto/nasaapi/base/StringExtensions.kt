package es.adriiiprieto.nasaapi.base

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun String.toMyDateFormat(): String {
    return try {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        val date = inputFormatter.parse(this)
        val newString = outputFormatter.format(date ?: "")

        newString
    }catch (e: Exception){
        this
    }
}