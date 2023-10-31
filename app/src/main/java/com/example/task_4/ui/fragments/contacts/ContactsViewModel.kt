package com.example.task_4.ui.fragments.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task_4.domain.localuserdataset.LocalContactData
import com.example.task_4.domain.model.Contact

class ContactsViewModel : ViewModel() {

    private val _contactList = MutableLiveData(LocalContactData().getLocalContactsList())
    val contactList: LiveData<List<Contact>> = _contactList

    private val _selectContacts = MutableLiveData<List<Contact>>(listOf())
    val selectContacts: LiveData<List<Contact>> = _selectContacts

    private val _isMultiselect = MutableLiveData(false)
    val isMultiselect: LiveData<Boolean> = _isMultiselect


    fun addContact(contact: Contact, position: Int = _contactList.value?.size ?: 0) : Boolean {
        val contactList = _contactList.value?.toMutableList() ?: mutableListOf()
        val a = listOf<Int>()
        if(!contactList.contains(contact)) {
            contactList.add(position, contact)
            _contactList.value = contactList
            return true
        }
        return  false
    }

    fun addSelectContact(contact: Contact): Boolean {
        val contactList = _selectContacts.value?.toMutableList() ?: mutableListOf()

        if(!contactList.contains(contact)) {
            contactList.add(contact)
            _selectContacts.value = contactList
            return true
        }
        return false
    }

    fun deleteSelectContact(contact: Contact): Boolean {
        val contactList = _selectContacts.value?.toMutableList() ?: return false
        if(contactList.contains(contact)){
            contactList.remove(contact)
            _selectContacts.value = contactList
            return true
        }
        return false
    }

    fun deleteContact(contact: Contact): Boolean {
        val contactList = _contactList.value?.toMutableList() ?: return false
        if(contactList.contains(contact)) {
            contactList.remove(contact)
            _contactList.value = contactList
            return true
        }
        return false
    }

    fun deleteSelectList() {
        val contactList = _selectContacts.value?.toMutableList() ?: return

        for(contact in contactList) {
            deleteSelectContact(contact)
            deleteContact(contact)
        }

        _selectContacts.value = contactList
    }

    fun changeMultiselectMode() {
        _isMultiselect.value = !isMultiselect.value!!
        if(isMultiselect.value == true) _selectContacts.value = emptyList()
    }

}