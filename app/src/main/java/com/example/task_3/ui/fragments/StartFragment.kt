package com.example.task_3.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task_3.R
import com.example.task_3.databinding.FragmentStartBinding
import com.example.task_3.domain.model.User
import com.example.task_3.ui.recycler_view.UserListAdapter
import com.example.task_3.ui.recycler_view.UserItemClickListener
import com.example.task_3.ui.recycler_view.UserViewModel
import com.example.task_3.utils.Constants
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private val adapter: UserListAdapter by lazy {
        UserListAdapter(object : UserItemClickListener {
            override fun onUserDeleteClick(contact: User, position: Int) {
                deleteUserWithRestore(contact, position)
            }

            override fun onUserClick(
                contact: User,
                transitionPairs: Array<Pair<View, String>>
            ) {
                val direction = StartFragmentDirections.actionStartFragmentToProfileFragment(contact)
                val extras = FragmentNavigatorExtras(*transitionPairs)
                findNavController().navigate(direction, extras)
            }

        })
    }

    private val viewModel : UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        initialRecyclerview()
        setClickListener()
    }

    private fun setObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.users.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun initialRecyclerview() {
        setTouchRecycleItemListener()
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewContacts.layoutManager = layoutManager
        binding.recyclerViewContacts.adapter = adapter
    }

    private fun setTouchRecycleItemListener() {
        val itemTouchCallback = setTouchCallBackListener()
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.recyclerViewContacts)
    }

    private fun setClickListener() {
        showAddContactsDialog()
    }

    private fun setTouchCallBackListener(): ItemTouchHelper.Callback {
        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteUserWithRestore(
                    viewModel.getUsersList()[viewHolder.bindingAdapterPosition],
                    viewHolder.bindingAdapterPosition
                )
            }
        }
    }

    private fun showAddContactsDialog() {
        binding.textViewAddContacts.setOnClickListener {
            Log.d("Aaaa", "add contact click")
            val dialogFragment = MyDialogFragment()
            dialogFragment.setViewModel(viewModel)
            dialogFragment.setAdapter(adapter)
            dialogFragment.show(parentFragmentManager, Constants.DIALOG_TAG)
        }
    }

    fun deleteUserWithRestore(contact: User, position: Int) {
        if (viewModel.deleteUser(contact)) {
            Snackbar.make(
                binding.recyclerViewContacts,
                getString(R.string.s_has_been_removed).format(contact.name),
                Snackbar.LENGTH_LONG
            )
                .setAction(getString(R.string.restore)) {
                    if (viewModel.addUser(contact, position)) {
                    }
                }.show()
        }
    }
}