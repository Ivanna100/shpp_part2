package com.example.task_4.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.task_4.databinding.FragmentUserProfileBinding
import com.example.task_4.ui.fragments.viewpager.ViewPagerFragment
import com.example.task_4.utils.Constants
import com.example.task_4.utils.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserProfile : BaseFragment<FragmentUserProfileBinding> (FragmentUserProfileBinding::inflate){

    private val args: UserProfileArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserProfile()
        setListeners()
    }

    private fun setListeners() {
        viewContacts()
        logOut()
    }

    //TODO extract this logic to data layer and viewModel
    private fun logOut() {
        binding.btnlogOut.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO) {
                DataStoreManager.deleteDataFromDataStore(requireContext(), Constants.KEY_EMAIL)
                DataStoreManager.deleteDataFromDataStore(requireContext(), Constants.KEY_REMEMBER_ME)

            }
            navController.navigateUp()
        }
    }

    private fun viewContacts() {
        binding.btnViewContacts.setOnClickListener{
            (parentFragment as? ViewPagerFragment)?.openFragment(1)
        }
    }

    private fun setUserProfile() {
        setUserName(args.email)
        setCareer()
        setAddress()
    }

    //TODO you should extract logic to separate place
    private fun setUserName(email: String) {
        val elements = email.split("@")[0].replace(".", " ").split(" ")
        binding.tvFullName.text = if(elements.size >= 2) {
            "${elements[0].replaceFirstChar { it.uppercase() }} ${elements[1].replaceFirstChar { it.titlecase() }}"
        } else {
            elements[0]
        }
    }

    private fun setCareer() {
        binding.tvCareer.text = ""
    }

    private fun setAddress() {
        binding.tvAddress.text = ""
    }
}