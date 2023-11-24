package com.example.task_3.ui.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.task_3.R
import com.example.task_3.databinding.FragmentDetailBinding
import com.example.task_3.data.model.Contact
import com.example.task_3.utils.Constants
import com.example.task_3.utils.ext.loadImage

class ContactProfile : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val args: ContactProfileArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contact = args.Contact
        setProfile(contact)
        setSharedElementsTransition(contact)
        setListeners()
    }

    private fun setSharedElementsTransition(contact: Contact) {
        with(binding) {
            imageViewAvatar.transitionName =
                Constants.TRANSITION_NAME_IMAGE + contact.id
            textViewFullName.transitionName = Constants.TRANSITION_NAME_NAME + contact.id
            textViewCareer.transitionName = Constants.TRANSITION_NAME_CAREER + contact.id
        }
        val animation = TransitionInflater.from(context).inflateTransition(
            R.transition.custom_move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    private fun setProfile(contact: Contact) {
        with(binding) {
            imageViewAvatar.loadImage(contact.photo)
            textViewFullName.text = contact.name
            textViewCareer.text = contact.career
            textViewAddress.text = contact.address
        }
    }

    private fun setListeners() {
        setNavigationBack()
    }

    private fun setNavigationBack() {
        binding.imageViewNavigationBack.setOnClickListener {
            navController.navigateUp()
        }
    }
}