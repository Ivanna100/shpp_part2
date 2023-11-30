package com.example.task_5.presentation.ui.fragments.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.task_3.databinding.FragmentSplashBinding
import com.example.task_5.data.model.UserWithTokens
import com.example.task_5.domain.state.UserApiResultState
import com.example.task_5.presentation.ui.fragments.BaseFragment
import com.example.task_5.utils.Constants
import com.example.task_5.utils.DataStore
import com.example.task_5.utils.ext.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel : SplashViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isAutoLogin()
        setObserver()
    }

    private fun isAutoLogin() {
        lifecycleScope.launch {
            val email = DataStore.getDataFromKey(requireContext(), Constants.KEY_EMAIL)
            val password = DataStore.getDataFromKey(requireContext(), Constants.KEY_PASSWORD)
            if(DataStore.getDataFromKey(requireContext(), Constants.KEY_REMEMBER_ME) != null &&
                email != null && password != null) {
                viewModel.autoLogin(email, password)
                } else {
                    val direction = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
                    navController.navigate(direction)
            }
        }
    }

    private fun setObserver() {
        lifecycleScope.launch {
            viewModel.authorizationState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                when(it) {
                    is UserApiResultState.Success -> {
                        val direction = SplashFragmentDirections.actionSplashFragmentToViewPagerFragment(
                            UserWithTokens(
                                it.data.user,
                                it.data.accessToken,
                                it.data.refreshToken
                            )
                        )
                        navController.navigate(direction)
                    }

                    is UserApiResultState.Loading -> Unit

                    is UserApiResultState.Initial -> Unit

                    is UserApiResultState.Error -> {
                        binding.root.showErrorSnackBar(requireContext(), it.error)
                    }
                }
            }
        }
    }

}