package com.example.task_5.presentation.ui.fragments.addcontacts.adapter.interfaces

import android.view.View
import com.example.task_5.data.model.Contact

interface UserItemClickListener {
    fun onClickAdd(contact: Contact)
    fun onClickContact(contact: Contact, transitionPairs: Array<Pair<View, String>>)
}