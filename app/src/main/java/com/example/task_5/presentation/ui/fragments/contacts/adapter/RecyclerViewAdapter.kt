package com.example.task_5.presentation.ui.fragments.contacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task_5.R
import com.example.task_5.data.model.Contact
import com.example.task_5.databinding.FragmentDialogCalendarBinding
import com.example.task_5.databinding.ItemUserBinding
import com.example.task_5.presentation.ui.fragments.contacts.adapter.interfaces.ContactItemClickListener
import com.example.task_5.presentation.ui.fragments.contacts.adapter.utils.ContactDiffUtil
import com.example.task_5.utils.Constants
import com.example.task_5.utils.ext.invisible
import com.example.task_5.utils.ext.loadImage
import com.example.task_5.utils.ext.visible

class RecyclerViewAdapter(private val listener: ContactItemClickListener) :
    ListAdapter<Contact, RecyclerViewAdapter.ContactsViewHolder>(ContactDiffUtil()) {

    private var isSelectItems: ArrayList<Pair<Boolean, Int>> = ArrayList()
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

        fun setListeners(contact: Contact) {
            if (isMultiselectMode) setSelectList(contact) else deleteItem(contact)
            itemLongClick(contact)
            itemClick(contact)
        }

        private fun setSelectList(contact: Contact) {
            with(binding) {
                checkboxSelectMode.visible()
                imageViewDelete.invisible()
                checkboxSelectMode.isChecked = isSelectItems.find {
                    it.second == contact.id.toInt()
                }?.first == true
                viewBorder.background = ContextCompat.getDrawable(
                    root.context,
                    R.drawable.bc_user_select_mode
                )
            }
        }

        private fun deleteItem(contact: Contact) {
            binding.imageViewDelete.setOnClickListener {
                listener.onClickDelete(contact)
            }
        }

        private fun itemClick(contact: Contact) {
            with(binding) {
                root.setOnClickListener {
                    if (isMultiselectMode) checkboxSelectMode.isChecked =
                        !checkboxSelectMode.isChecked
                    listener.onClickContact(
                        contact, arrayOf(
                            setTransitionName(
                                imageViewUserPhoto,
                                Constants.TRANSITION_NAME_IMAGE + contact.id
                            ),
                            setTransitionName(
                                textViewName,
                                Constants.TRANSITION_NAME_NAME + contact.id
                            ),
                            setTransitionName(
                                textViewCareer,
                                Constants.TRANSITION_NAME_CAREER + contact.id
                            )
                        )
                    )
                }
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

    fun multiselectData(isMultiselectItem : ArrayList<Pair<Boolean, Int>>) {
        this.isSelectItems = isMultiselectItem
    }
}