package com.example.task_4.ui.fragments.contacts.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.task_4.domain.model.Contact


class ContactDiffUtil : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }
}