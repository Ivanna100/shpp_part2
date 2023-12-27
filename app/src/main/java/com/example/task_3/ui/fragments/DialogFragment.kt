package com.example.task_3.ui.fragments

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.task_3.R
import com.example.task_3.data.model.Contact
import com.example.task_3.databinding.FragmentAddContactBinding
import com.example.task_3.utils.ext.loadImage

class DialogFragment: AppCompatDialogFragment() {

    private lateinit var binding: FragmentAddContactBinding

    private var photoUri: Uri? = null

    private lateinit var listener: DialogInterface

    private val requestImageLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            uri -> uri?.let {
                photoUri = it
                binding.imageViewNewContactPhoto.loadImage(it.toString())
                binding.imageViewNewContactMockup.visibility = View.GONE
            }
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.fragment_add_contact, null)
        builder.setView(dialogView)
        binding = FragmentAddContactBinding.bind(dialogView)
        setListeners()
        return builder.create()
    }

    private fun setListeners() {
        setPhoto()
        navigationBack()
        saveNewContact()
    }

    private fun setPhoto() {
        binding.imageViewAddPhoto.setOnClickListener{
            val request = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            requestImageLauncher.launch(request)
        }
    }

    private fun navigationBack() {
        binding.imageViewNavigationBack.setOnClickListener{
            dismiss()
        }
    }

    fun setDialogListener( listener : DialogInterface) {
        this.listener = listener
    }

    private fun saveNewContact() {
        binding.buttonSave.setOnClickListener{
            listener.onSendContact(
                Contact(
                    name = binding.textInputEditTextUserName.text.toString(),
                    career = binding.textInputEditTextCareer.text.toString(),
                    photo = photoUri.toString(),
                    email = binding.textInputEditTextEmail.text.toString(),
                    phone = binding.textInputEditTextPhone.text.toString(),
                    address = binding.textInputEditTextAddress.text.toString(),
                    date = binding.textInputEditTextDateOfBirth.text.toString()
                )
            )
            dismiss()
        }
    }
}