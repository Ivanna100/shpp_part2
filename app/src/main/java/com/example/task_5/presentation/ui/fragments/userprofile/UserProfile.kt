package com.example.task_5.presentation.ui.fragments.userprofile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.task_5.databinding.FragmentProfileBinding
import com.example.task_5.data.model.UserData
import com.example.task_5.domain.state.UserApiResultState
import com.example.task_5.presentation.ui.fragments.BaseFragment
import com.example.task_5.presentation.ui.fragments.viewpager.ViewPagerFragment
import com.example.task_5.presentation.ui.fragments.viewpager.ViewPagerFragmentDirections
import com.example.task_5.utils.Constants
import com.example.task_5.utils.DataStore
import com.example.task_5.utils.ext.invisible
import com.example.task_5.utils.ext.showErrorSnackBar
import com.example.task_5.utils.ext.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserProfile : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate){

    private val args: UserProfileArgs by navArgs()
    private val viewModel : UserProfileViewModel by viewModels()
    private lateinit var user : UserData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialUser()
        setListeners()
        setUserProfile()
        setObserver()
    }

    private fun initialUser() {
        viewModel.getUser(args.userProfile.user.id, args.userProfile.accessToken)
        user = args.userProfile.user
    }

    private fun setListeners() {
        viewContacts()
        logOut()
        editProfile()
    }

    private fun viewContacts() {
        binding.buttonViewContacts.setOnClickListener{
            (parentFragment as? ViewPagerFragment)?.openFragment(Constants.SECOND_FRAGMENT)
        }
    }

    private fun logOut() {
        binding.textViewLogout.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO) {
                DataStore.deleteDataFromDataStore(requireContext(), Constants.KEY_EMAIL)
                DataStore.deleteDataFromDataStore(requireContext(), Constants.KEY_PASSWORD)
                DataStore.deleteDataFromDataStore(requireContext(), Constants.KEY_REMEMBER_ME)
            }
            val direction = ViewPagerFragmentDirections.actionViewPagerFragmentToSignInFragment()
            navController.navigate(direction)
        }
    }

    private fun editProfile() {
        binding.buttonEditProfile.setOnClickListener {
//            val direction = ViewPagerFragmentDirections.act
        }
    }

    private fun setUserProfile() {
        with(binding) {
            textViewName.text = user.name
            textViewCareer.text = user.career
            textViewHomeAddress.text = user.address
        }
    }

    private fun setObserver() {
        lifecycleScope.launch {
            viewModel.getUserState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect{
                when(it) {
                    is UserApiResultState.Success -> {
                        with(binding) {
                            textViewCareer.visible()
                            textViewHomeAddress.visible()
                            progressBar.invisible()
                        }
                        user = it.data.user
                        setUserProfile()
                    }

                    is UserApiResultState.Initial -> Unit

                    is UserApiResultState.Loading -> {
                        binding.progressBar.visible()
                    }

                    is UserApiResultState.Error -> {
                        binding.root.showErrorSnackBar(requireContext(), it.error)
                    }
                }
            }
        }
    }

}