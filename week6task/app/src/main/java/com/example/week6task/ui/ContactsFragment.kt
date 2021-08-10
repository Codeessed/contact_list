package com.example.week6task.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.week6task.R
import com.example.week6task.databinding.FragmentContactsBinding


class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private var adapter = ContactAdapter()

    private lateinit var viewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)




        viewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewContacts.adapter = adapter

        binding.floatButton.setOnClickListener {
            AddContactDialogFragment().show(childFragmentManager, "")
        }


        viewModel.contact.observe(viewLifecycleOwner, Observer {
            adapter.addContact(it)
        })

        viewModel.getRealTimeUpdate()

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewContacts)
    }


    private val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            var position = viewHolder.adapterPosition
            var currentContact = adapter.contacts[position]

            when(direction){
                ItemTouchHelper.RIGHT -> {
                    UpdateContactDialogFragment(currentContact).show(childFragmentManager, "")
                }
                ItemTouchHelper.LEFT ->{
                    AlertDialog.Builder(requireContext()).also {
                        it.setTitle("Are you sure you want to delete this contact?")
                        it.setPositiveButton("Yes"){dialog, which->
                            viewModel.deleteContact(currentContact)
                            binding.recyclerViewContacts.adapter?.notifyItemRemoved(position)
                            Toast.makeText(context, "Contact has been deleted", Toast.LENGTH_SHORT).show()
                        }
                    }.create().show()
                }
            }

            binding.recyclerViewContacts.adapter?.notifyDataSetChanged()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null // so that there won't be many binding objects
    }


}