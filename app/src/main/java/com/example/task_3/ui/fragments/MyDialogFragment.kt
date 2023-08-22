package com.example.task_3.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.task_3.databinding.FragmentAddUserBinding
import com.example.task_3.domain.model.User
import com.example.task_3.ui.recycler_view.RecyclerViewAdapter
import com.example.task_3.ui.recycler_view.UserItemClickListener
import com.example.task_3.ui.recycler_view.UserViewModel

class MyDialogFragment: DialogFragment() {
//    : AppCompatDialogFragment() {

    private var userViewModel = UserViewModel()
    fun setViewModel(userViewModel: UserViewModel) {
        this.userViewModel = userViewModel
    }

    private lateinit var binding: FragmentAddUserBinding

//    private var adapter = RecyclerViewAdapter(object : UserItemClickListener {
    private lateinit var adapter: RecyclerViewAdapter

    fun setAdapter(recyclerViewAdapter: RecyclerViewAdapter) {
        adapter = recyclerViewAdapter
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentAddUserBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireActivity())
//        val inflater = requireActivity().layoutInflater
//        val dialogView = inflater.inflate(R.layout.fragment_add_user, null)
        builder.setView(binding.root)
//        binding = FragmentAddUserBinding.bind(dialogView)
        setListeners()
        Log.d("Aaaa", "on create dialog")
        return builder.create()
    }

    private fun setListeners() {
        buttonAddListener()
        buttonCancelListener()
    }

    private fun buttonAddListener() {
        with(binding) {
            buttonAdd.setOnClickListener {
                userViewModel.addUser(
                    User(
                        textInputEditTextFullName.text.toString(),
                        textInputEditTextCareer.text.toString(),
                        ""
                    ),userViewModel.getUsersList().size
                )
                adapter.updateUsers(userViewModel.getUsersList())
                adapter.notifyItemInserted(userViewModel.getUsersList().size - 1)
                dismiss()
            }
        }
    }

    private fun buttonCancelListener() {
        binding.buttonCancel.setOnClickListener {
            dismiss() }
    }
}