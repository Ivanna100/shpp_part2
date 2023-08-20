package com.example.task_3.ui.fragments

import android.app.Dialog
import android.os.Bundle

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.task_3.R
import com.example.task_3.databinding.FragmentAddUserBinding
import com.example.task_3.domain.model.User
import com.example.task_3.ui.recycler_view.RecyclerViewAdapter
import com.example.task_3.ui.recycler_view.UserViewModel

class DialogFragment : AppCompatDialogFragment() {

    private var userViewModel = UserViewModel()
    fun setViewModel(userViewModel: UserViewModel) {
        this.userViewModel = userViewModel
    }

    private lateinit var binding: FragmentAddUserBinding

    private var adapter = RecyclerViewAdapter()
    fun setAdapter(recyclerViewAdapter: RecyclerViewAdapter) {
        adapter = recyclerViewAdapter
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.fragment_add_user, null)
        builder.setView(dialogView)
        binding = FragmentAddUserBinding.bind(dialogView)
        setListeners()
        return builder.create()
    }

    fun setListeners() {
        buttonAddListener()
        buttonCancelListener()
    }

    fun buttonAddListener() {
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

    fun buttonCancelListener() {
        dismiss()
    }
}