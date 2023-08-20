package com.example.task_3.ui.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.task_3.R
import com.example.task_3.databinding.FragmentDetailBinding
import com.example.task_3.domain.model.User
import com.example.task_3.utils.Constants
import com.example.task_3.utils.ext.loadImage

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val contact = args.user
        setProfile(contact)
        setSharedElementsTransition(contact)
        setNavigationBack()
        return binding.root
    }
    private fun setProfile(contact: User) {
        with(binding) {
            imageViewAvatar.loadImage(contact.photo)
            textViewFullName.text = contact.name
            textViewCareer.text = contact.career
        }
    }
    private fun setSharedElementsTransition(contact: User) {
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


//    private fun setListeners() {
//        setNavigationBack()
//    }

    private fun setNavigationBack() {
        binding.imageViewNavigationBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}