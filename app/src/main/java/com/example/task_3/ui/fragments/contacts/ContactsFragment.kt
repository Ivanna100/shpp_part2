package com.example.task_3.ui.fragments.contacts

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task_3.R
import com.example.task_3.databinding.FragmentContactsBinding
import com.example.task_3.data.model.Contact
import com.example.task_3.ui.fragments.BaseFragment
import com.example.task_3.ui.fragments.DialogInterface
import com.example.task_3.ui.fragments.DialogFragment
import com.example.task_3.ui.fragments.viewpager.ViewPagerFragment
import com.example.task_3.ui.fragments.viewpager.ViewPagerFragmentDirections
import com.example.task_3.utils.Constants
import com.example.task_3.utils.Fragments
import com.example.task_3.utils.ext.showErrorSnackBar
import com.example.task_3.utils.setTouchCallBackListener
import com.google.android.material.snackbar.Snackbar

class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding:: inflate),
    DialogInterface {

    private val viewModel: ContactsViewModel by viewModels()

    private val adapter: ContactsListAdapter by lazy {
        ContactsListAdapter(listener = object : ContactItemClickListener {

            override fun onClickDelete(contact: Contact) {
                deleteUserWithRestore(contact)
            }

            override fun onClickContact(
                contact: Contact,
                transitionPairs: Array<Pair<View, String>>
            ) {
                if(adapter.isMultiselectMode) {
                    viewModel.makeMultiselectOperations(contact)
                } else {
                    val extras = FragmentNavigatorExtras(*transitionPairs)
                     val direction =
                         ViewPagerFragmentDirections.actionViewPagerFragmentToContactProfile(contact)
                    navController.navigate(direction, extras)
                }
            }

            override fun onLongClick(contact: Contact) {
                removeChecked()
                contact.isChecked = true
                viewModel.changeMultiselectMode()
                if(viewModel.isMultiselect.value == true) viewModel.addSelectContact(contact)
                else removeChecked()
            }

            override fun onOpenNewFragment(
                contact: Contact,
                transitionPairs: Array<Pair<View, String>>
            ) {
                val direction =
                    ViewPagerFragmentDirections.actionViewPagerFragmentToContactProfile(contact)
                val extras = FragmentNavigatorExtras(*transitionPairs)
                navController.navigate(direction, extras)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialRecyclerview()
        setClickListener()
        setObservers()
    }

    private fun setObservers() {
        viewModel.contactList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.isMultiselect.observe(viewLifecycleOwner) {
            binding.recyclerViewContacts.adapter = adapter
            adapter.isMultiselectMode = it
            with(binding) {
                textViewAddContacts.visibility = if(it) View.GONE else View.VISIBLE
                imageViewDeleteSelectMode.visibility = if(it) View.VISIBLE else View.GONE
            }

        }
    }

    private fun initialRecyclerview() {
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewContacts.layoutManager = layoutManager
        binding.recyclerViewContacts.adapter = adapter
        ItemTouchHelper(setTouchCallBackListener(onSwiped = {
            deleteUserWithRestore(viewModel.contactList.value?.getOrNull(it)!!)
        }, isSwipeEnabled = {
            viewModel.isMultiselect.value == false
        })).attachToRecyclerView(binding.recyclerViewContacts)
    }

    private fun setClickListener() {
        navigationBack()
        showAddContactsDialog()
        deleteSelectedContacts()
    }

    private fun showAddContactsDialog() {
        binding.textViewAddContacts.setOnClickListener {
            val dialogFragment = DialogFragment()
            dialogFragment.setDialogListener(listener = this)
            dialogFragment.show(parentFragmentManager, Constants.DIALOG_TAG)
        }
    }

    private fun navigationBack() {
        binding.imageViewNavigationBack.setOnClickListener{
            (parentFragment as ViewPagerFragment)?.openFragment(Fragments.USER_PROFILE.ordinal)
        }
        val callback = object : OnBackPressedCallback (true) {
            override fun handleOnBackPressed() {
                (requireParentFragment() as? ViewPagerFragment)?.openFragment(Fragments.USER_PROFILE.ordinal)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun deleteSelectedContacts() {
        binding.imageViewDeleteSelectMode.setOnClickListener{
            val size = viewModel.selectContacts.value?.size
            viewModel.deleteSelectList()
            binding.root.showErrorSnackBar(requireContext(), if(size!! > 1) R.string.contacts_removed
                else R.string.contact_removed)
            viewModel.changeMultiselectMode()
        }
    }

    private fun removeChecked() {
        viewModel.contactList.value?.forEach { contact ->
            contact.isChecked = false
        }
    }

    fun deleteUserWithRestore(contact: Contact) {
        val position = getPosition(contact)
        if(viewModel.deleteContact(contact)) {
            Snackbar.make(
                binding.recyclerViewContacts,
                getString(R.string.s_has_been_removed).format(contact.name),
                Snackbar.LENGTH_LONG
            ).setAction(getString(R.string.restore)) {
                viewModel.addContact(contact, position)
            }.show()
        }
    }

    private fun getPosition(currentContact: Contact): Int {
        val contactList = viewModel.contactList.value ?: emptyList()
        return contactList.indexOfFirst { it == currentContact }
    }

    override fun onSendContact(contact: Contact) {
        viewModel.addContact(contact)
    }
}