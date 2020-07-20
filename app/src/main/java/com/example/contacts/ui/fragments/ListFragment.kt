package com.example.contacts.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contacts.R
import com.example.contacts.adapters.ContactAdapter
import com.example.contacts.data.User
import com.example.contacts.presenters.ContactsView
import com.example.contacts.ui.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(), ContactsView {
    private val listPresenter = com.example.contacts.presenters.ListPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listPresenter.attachView(view = this)
       // createRecyclerView()
        listPresenter.render()
    }

    override fun changeFragment(fragment: Fragment) {
        val transaction = (activity as MainActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.list_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun showContacts(users: List<User>, callback : ContactAdapter.Callback) {
        val contactAdapter = ContactAdapter(users, callback)
        contact_list.layoutManager = LinearLayoutManager(activity as MainActivity)
        contact_list.adapter = contactAdapter
    }

}