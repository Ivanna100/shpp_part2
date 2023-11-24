package com.example.task_3.ui.fragments.contacts

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.example.task_3.domain.localuserdataset.LocalContactData
import com.example.task_3.domain.model.Contact

class ContactsViewModel : ViewModel() {

    private val _contactList = MutableLiveData(listOf<Contact>())

    val contactList: LiveData<List<Contact>> get() = _contactList

    private val _selectContacts = MutableLiveData<List<Contact>>(listOf())
    val selectContacts: LiveData<List<Contact>> get() = _selectContacts

    val isMultiselect = MutableLiveData(false)

//    private val listener = object : ContactItemClickListener {
//
//            override fun onClickDelete(contact: Contact) {
//                deleteUserWithRestore(contact)
//            }
//
//            override fun onClickContact(
//                contact: Contact,
//                transitionPairs: Array<Pair<View, String>>
//            ) {
//                if(adapter.isMultiselectMode) {
//                    //TODO you should extract logic of operating with list to view model
////                    viewModel.onClickedInMultiselectMode(
////                        contact
////                    )
//                    contact.isChecked = !contact.isChecked
//                    if(contact.isChecked && viewModel.selectContacts.value?.contains(contact) == false)
//                        viewModel.addSelectContact(contact)
//
//                    if(!contact.isChecked && viewModel.selectContacts.value?.contains(contact) == true)
//                        viewModel.deleteSelectContact(contact)
//
//                    if(viewModel.selectContacts.value?.size == 0 )
//                        viewModel.changeMultiselectMode()
//                } else {
//                    val extras = FragmentNavigatorExtras(*transitionPairs)
//                    val direction =
//                        ViewPagerFragmentDirections.actionViewPagerFragmentToContactProfile(contact)
//                    navController.navigate(direction, extras)
//                }
//            }
//
//            override fun onLongClick(contact: Contact) {
//                removeChecked()
//                contact.isChecked = true
//                viewModel.changeMultiselectMode()
//                if(viewModel.isMultiselect.value == true) viewModel.addSelectContact(contact)
//                else removeChecked()
//            }
//
//            override fun onOpenNewFragment(
//                contact: Contact,
//                transitionPairs: Array<Pair<View, String>>
//            ) {
//                val direction =
//                    ViewPagerFragmentDirections.actionViewPagerFragmentToContactProfile(contact)
//                val extras = FragmentNavigatorExtras(*transitionPairs)
//                navController.navigate(direction, extras)
//            }
//        })
//    }

    init {
        _contactList.value = LocalContactData().getLocalContactsList().toMutableList()
    }

    fun addContact(contact: Contact, position: Int = _contactList.value?.size ?: 0) : Boolean {
        val contactList = _contactList.value?.toMutableList() ?: mutableListOf()

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
        isMultiselect.value = !isMultiselect.value!!
        if(isMultiselect.value == true) _selectContacts.value = emptyList()
    }

}