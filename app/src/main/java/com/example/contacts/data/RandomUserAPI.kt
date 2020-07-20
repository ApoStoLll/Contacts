package com.example.contacts.data

import retrofit2.Call
import retrofit2.http.GET

interface RandomUserAPI{
    @GET("api")
    fun getContacts() : Call<Contact>
}