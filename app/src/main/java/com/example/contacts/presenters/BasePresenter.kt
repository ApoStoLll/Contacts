package com.example.contacts.presenters

import androidx.fragment.app.Fragment
import com.example.contacts.adapters.ContactAdapter
import com.example.contacts.data.Contact
import com.example.contacts.data.User

interface BasePresenter {
    fun render()
}

interface ContactsView{
    fun showContacts(users : List<User>, callback: ContactAdapter.Callback)
    fun changeFragment(fragment: Fragment)
}