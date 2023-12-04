package com.example.task_5.presentation.ui.fragments.addcontacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task_5.data.model.Contact
import com.example.task_5.databinding.ItemAddUserBinding
import com.example.task_5.domain.state.ArrayDataApiResultState
import com.example.task_5.presentation.ui.fragments.addcontacts.adapter.interfaces.UserItemClickListener
import com.example.task_5.presentation.ui.fragments.contacts.adapter.utils.ContactDiffUtil
import com.example.task_5.utils.ext.gone
import com.example.task_5.utils.ext.invisible
import com.example.task_5.utils.ext.loadImage
import com.example.task_5.utils.ext.visible

class AddContactsAdapter (
    private val listener: UserItemClickListener
    ) : ListAdapter<Contact, AddContactsAdapter.ContactsViewHolder> (ContactDiffUtil())
{

    private  var states : ArrayList<Pair<Long, ArrayDataApiResultState>> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAddUserBinding.inflate(inflater, parent, false)
        return  ContactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(
            currentList[position],
            states.find { it.first == currentList[position].id }?.second
                ?: ArrayDataApiResultState.Initial
        )
    }

    inner class ContactsViewHolder(private val binding: ItemAddUserBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bind(contact: Contact, state : ArrayDataApiResultState) {
                with(binding) {
                    textViewName.text = contact.name
                    textViewCareer.text = contact.career
                    imageViewUserPhoto.loadImage(contact.photo)
                }
                setState(state)
                setListeners(contact)
            }

        private fun setState(state : ArrayDataApiResultState) {
            with(binding) {
                when(state) {
                    is ArrayDataApiResultState.Success -> {
                        textViewAdd.gone()
                        progressBar.gone()
                        imageViewDoneAddContact.visible()
                    }

                    is ArrayDataApiResultState.Initial-> {
                        textViewAdd.visible()
                        progressBar.gone()
                        imageViewDoneAddContact.invisible()
                    }

                    is ArrayDataApiResultState.Loading -> {
                        textViewAdd.gone()
                        progressBar.visible()
                        imageViewDoneAddContact.invisible()
                    }

                    is ArrayDataApiResultState.Error -> Unit
                }
            }
        }

        private fun setListeners(contact: Contact) {
            addContact(contact)
            detailView(contact)
        }

        private fun addContact(contact: Contact) {
            binding.textViewAdd.setOnClickListener {
                listener.onClickAdd(contact)
            }
        }

        private fun detailView(contact: Contact) {
            with(binding) {
                root.setOnClickListener {
                    listener.onClickContact(
                        contact,
                        arrayOf(
                            setTransitionName(
                                imageViewUserPhoto,
                                com.example.task_5.utils.Constants.TRANSITION_NAME_IMAGE + contact.id,
                            ),
                            setTransitionName(
                                textViewName,
                                com.example.task_5.utils.Constants.TRANSITION_NAME_NAME + contact.id
                            ),
                            setTransitionName(
                                textViewCareer,
                                com.example.task_5.utils.Constants.TRANSITION_NAME_CAREER + contact.id
                            )
                        )
                    )
                }
            }
        }

        private fun setTransitionName(view: View, name : String) : Pair<View, String> {
            view.transitionName = name
            return view to name
        }
    }

    fun setStates(states : ArrayList<Pair<Long, ArrayDataApiResultState>>) {
        if(this.states.size != states.size) {
            this.states = states
            val lastIndex = currentList.indexOfLast { it.id == states.lastOrNull()?.first }
            if(lastIndex != -1) {
                notifyItemChanged(lastIndex)
            }
            return
        }
        states.forEachIndexed{ index, state ->
            if(this.states[index] != states[index]) {
                this.states[index] = state
                notifyItemChanged(currentList.indexOfFirst { it.id == state.first })
            }
        }
    }

}