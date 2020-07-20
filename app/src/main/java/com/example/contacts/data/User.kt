package com.example.contacts.data

import android.graphics.Bitmap

data class User(val name : String, val age : Int, val mobile : String, val email : String,
                val bmpS : Bitmap?, val bmpL : Bitmap?, val imgSrc : String)