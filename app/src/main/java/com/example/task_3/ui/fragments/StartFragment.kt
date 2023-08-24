package com.example.task_3.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task_3.R
import com.example.task_3.databinding.FragmentStartBinding
import com.example.task_3.domain.model.User
import com.example.task_3.ui.recycler_view.RecyclerViewAdapter
import com.example.task_3.ui.recycler_view.UserItemClickListener
import com.example.task_3.ui.recycler_view.UserViewModel
import com.example.task_3.utils.Constants
import com.google.android.material.snackbar.Snackbar

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private val adapter: RecyclerViewAdapter by lazy {
        RecyclerViewAdapter(object : UserItemClickListener {
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

    private var userViewModel = UserViewModel()

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
        initialRecyclerview()
        setClickListener()
    }

    private fun initialRecyclerview() {
        setTouchRecycleItemListener()
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewContacts.layoutManager = layoutManager
        binding.recyclerViewContacts.adapter = adapter
        adapter.updateUsers(userViewModel.getUsersList())
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
                    userViewModel.getUsersList()[viewHolder.adapterPosition],
                    viewHolder.adapterPosition
                )
            }
        }
    }

    private fun showAddContactsDialog() {
        binding.textViewAddContacts.setOnClickListener {
            Log.d("Aaaa", "add contact click")
            val dialogFragment = MyDialogFragment()
            dialogFragment.setViewModel(userViewModel)
            dialogFragment.setAdapter(adapter)
            dialogFragment.show(parentFragmentManager, Constants.DIALOG_TAG)
        }
    }

    fun deleteUserWithRestore(contact: User, position: Int) {
        if (userViewModel.deleteUser(contact)) {
            adapter.notifyItemRemoved(position)
            adapter.updateUsers(userViewModel.getUsersList())
            Snackbar.make(
                binding.recyclerViewContacts,
                getString(R.string.s_has_been_removed).format(contact.name),
                Snackbar.LENGTH_LONG
            )
                .setAction(getString(R.string.restore)) {
                    if (userViewModel.addUser(contact, position)) {
                        adapter.notifyItemInserted(position)
                        adapter.updateUsers(userViewModel.getUsersList())
                    }
                }.show()
        }
    }
}