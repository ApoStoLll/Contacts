package com.example.contacts.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.data.User
import javax.security.auth.callback.Callback

class ContactAdapter(var items : List<User>, val callback : Callback) : RecyclerView.Adapter< ContactAdapter.ContactViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder
        = ContactViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ContactViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.name)
        val img = itemView.findViewById<ImageView>(R.id.imgS)

        fun bind(item : User){
            //name.text = item.results[0].name.first + " " + item.results[0].name.last
            name.text = item.name
            if (item.bmpS != null ) img.setImageBitmap(item.bmpS)
            itemView.setOnClickListener{
                if(adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onItemClicked(item : User)
    }
}