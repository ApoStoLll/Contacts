package com.example.contacts.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.contacts.R
import com.example.contacts.data.Client
import com.example.contacts.presenters.ContactsView
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Это мем код, просто надо закончить а я устал, нужно было сделать презентер + парселайбл
        val name = arguments?.getString("name")
        val age = arguments?.getInt("age")
        val mobile = arguments?.getString("mobile")
        val email = arguments?.getString("email")
        val imgSrc = arguments?.getString("imgL")

        profile_name.text = name
        profile_age.text = age.toString()
        profile_phone.text = mobile
        profile_email.text = email
        CoroutineScope(Dispatchers.IO).async{
            val client = Client()
            if (imgSrc != null) {
                val bmp = client.getBitmap(imgSrc).await()
                withContext(Dispatchers.Main){
                    imgL.setImageBitmap(bmp)
                }
            }
        }
    }
}