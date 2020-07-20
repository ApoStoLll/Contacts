package com.example.contacts.presenters

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import com.example.contacts.adapters.ContactAdapter
import com.example.contacts.data.Client
import com.example.contacts.data.Contact
import com.example.contacts.data.RandomUserAPI
import com.example.contacts.data.User
import com.example.contacts.ui.fragments.ProfileFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference
import java.util.EnumSet.range

class ListPresenter : BasePresenter, ContactAdapter.Callback{
    
    private var view : WeakReference<ContactsView>? = null
    
    fun attachView(view : ContactsView){
        this.view = WeakReference(view)
    }
    
    override fun render() {
        CoroutineScope(Dispatchers.IO).async{
            val users = getUsers()
            withContext(Dispatchers.Main){
                view?.get()?.showContacts(users, this@ListPresenter)
            }
        }
    }

    private suspend fun getUsers() : List<User>{
        val users = mutableListOf<User>()
        val client = Client()
        val contact = client.getContact().await()
        Log.e("KEK", contact.toString())
        return if(contact != null) {
            Log.e("PResenter", contact.toString())
            val bmpS = client.getBitmap(contact.results[0].picture.medium).await()
            val bmpL = client.getBitmap(contact.results[0].picture.large).await()
            val user = contactToUser(contact = contact, bmpS = bmpS, bmpL = bmpL)
            for (i in 0..20)
                users.add(user)
            users
        } else getUsers()
    }

    private fun contactToUser(contact : Contact, bmpS : Bitmap?, bmpL : Bitmap?) : User {
        val res = contact.results[0]
        val name = res.name.first + " " + res.name.last
        return User(name = name, age = res.dob.age, mobile = res.phone, email = res.email,
            bmpL = bmpL, bmpS = bmpS, imgSrc = res.picture.large)
    }

    override fun onItemClicked(item: User) {
        val profile = ProfileFragment()
        val bundle = Bundle()
        bundle.putString("imgL", item.imgSrc)
        bundle.putString("name", item.name)
        bundle.putInt("age", item.age)
        bundle.putString("mobile", item.mobile)
        bundle.putString("email", item.email)
        profile.arguments = bundle
        view?.get()?.changeFragment(profile)
    }
}

