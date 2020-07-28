package com.example.aleks.behancer_kotlin.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatTime (time:Long):String{
    val date = Date(time * 1000L)
    val sdf = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())
    return sdf.format(date)
}


