package com.example.week6task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.week6task.R
import com.example.week6task.data.Contact
import com.example.week6task.databinding.FragmentAddContactDialogBinding
import com.example.week6task.databinding.FragmentContactsBinding



class AddContactDialogFragment : DialogFragment() {
    private var _binding: FragmentAddContactDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddContactDialogBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.result.observe(viewLifecycleOwner, Observer{
            val message = if (it == null ){
                getString(R.string.added_contact)
            }else{
                getString(R.string.error, it.message)
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            dismiss()
        })

        binding.button.setOnClickListener {
            val firstName = binding.editFirstName.text.toString().trim()
            val lastName = binding.editLastName.text.toString().trim()
            val phoneNumber = binding.editPhoneText.text.toString().trim()

            if(firstName.isEmpty()){
                binding.editFirstName.error = "This field is required"
                return@setOnClickListener
            }
            if(lastName.isEmpty()){
                binding.editLastName.error = "This field is required"
                return@setOnClickListener
            }
            if(phoneNumber.isEmpty()){
                binding.editPhoneText.error = "This field is required"
                return@setOnClickListener
            }

            val contact = Contact()
            contact.firstName = firstName
            contact.lastName = lastName
            contact.phoneNumber = phoneNumber

            viewModel.addContact(contact)

        }

    }


}