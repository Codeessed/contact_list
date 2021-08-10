package com.example.week6task.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week6task.data.Contact
import com.example.week6task.databinding.RecyclerViewContactBinding

//extend recyclerview and access our adapter from the recyclerview and the adapter is a type of this class
class ContactAdapter:RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    var contacts = mutableListOf<Contact>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerViewContactBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.firstNameText.text = contacts[position].firstName
        holder.binding.lastNameText.text = contacts[position].lastName
        holder.binding.phoneText.text = contacts[position].phoneNumber

    }

    override fun getItemCount(): Int {
        return contacts.size

    }
    fun addContact(contact: Contact){
        if(!contacts.contains(contact)){
            contacts.add(contact)
        }else{
            val index = contacts.indexOf(contact)
            if (contact.isDeleted){
                contacts.removeAt(index)
            }else{
                contacts[index] = contact
            }

        }
    }

    inner class ViewHolder(val binding:RecyclerViewContactBinding):RecyclerView.ViewHolder(binding.root){

    }
}