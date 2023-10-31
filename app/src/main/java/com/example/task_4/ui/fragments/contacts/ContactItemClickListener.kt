package com.example.task_4.ui.fragments.contacts

import android.view.View
import com.example.task_4.domain.model.Contact

interface ContactItemClickListener {
    fun onClickDelete(contact: Contact)
    fun onClickContact(contact: Contact,transitionPairs: Array<Pair<View, String>>)
    fun onLongClick(contact: Contact)
    fun onOpenNewFragment(contact: Contact, transitionPairs: Array<Pair<View, String>>)
}