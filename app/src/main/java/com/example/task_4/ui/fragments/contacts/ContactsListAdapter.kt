package com.example.task_4.ui.fragments.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task_4.R
import com.example.task_4.databinding.ItemUserBinding
import com.example.task_4.domain.model.Contact
import com.example.task_4.ui.fragments.contacts.utils.ContactDiffUtil
import com.example.task_4.utils.Constants
import com.example.task_4.utils.ext.loadImage


class ContactsListAdapter( private val listener: ContactItemClickListener) :
    ListAdapter<Contact, ContactsListAdapter.ContactsViewHolder>(ContactDiffUtil()) {

    var isMultiselectMode = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return ContactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ContactsViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            with(binding) {
                textViewName.text = contact.name
                textViewCareer.text = contact.career
                imageViewUserPhoto.loadImage(contact.photo)
            }
            setListeners(contact)
        }

        private fun setListeners(contact: Contact) {
            if (isMultiselectMode) setSelectList(contact) else deleteItem(contact)
            itemLongClick(contact)
            itemClick(contact)
        }

        private fun deleteItem(contact: Contact) {
            binding.imageViewDelete.setOnClickListener {
                listener.onClickDelete(contact)
            }
        }
        private fun itemClick(contact: Contact) {
            with(binding) {
                root.setOnClickListener {
                    if(isMultiselectMode) checkboxSelectMode.isChecked =
                        !contact.isChecked
                    listener.onClickContact(
                        contact, arrayOf(
                        setTransitionName(
                            imageViewUserPhoto,
                            Constants.TRANSITION_NAME_IMAGE + contact.id),
                            setTransitionName(
                                textViewName,
                            Constants.TRANSITION_NAME_NAME + contact.id),
                            setTransitionName(
                                textViewCareer,
                            Constants.TRANSITION_NAME_CAREER + contact.id)
                    )
                    )
                }
            }
        }

        private fun setSelectList(contact: Contact) {
            with(binding) {
                checkboxSelectMode.visibility = View.VISIBLE
                imageViewDelete.visibility = View.GONE
                checkboxSelectMode.isChecked = contact.isChecked
                viewBorder.background =
                    ContextCompat.getDrawable(root.context, R.drawable.bc_user_select_mode)
            }
        }

        private fun itemLongClick(contact: Contact) {
            binding.itemUser.setOnLongClickListener {
                listener.onLongClick(contact)
                true
            }
        }

        private fun setTransitionName(view: View, name: String): Pair<View, String> {
            view.transitionName = name
            return view to name
        }
    }

}