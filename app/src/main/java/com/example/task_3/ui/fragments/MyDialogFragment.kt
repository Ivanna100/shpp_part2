package com.example.task_3.ui.fragments

import android.app.Dialog
import android.os.Bundle

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.task_3.databinding.FragmentAddUserBinding
import com.example.task_3.domain.model.User
import com.example.task_3.ui.recycler_view.RecyclerViewAdapter
import com.example.task_3.ui.recycler_view.UserViewModel

class MyDialogFragment: AppCompatDialogFragment() {

    private var userViewModel = UserViewModel()
    fun setViewModel(userViewModel: UserViewModel) {
        this.userViewModel = userViewModel
    }

    private lateinit var binding: FragmentAddUserBinding
    private lateinit var adapter: RecyclerViewAdapter

    fun setAdapter(recyclerViewAdapter: RecyclerViewAdapter) {
        adapter = recyclerViewAdapter
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentAddUserBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        setListeners()
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