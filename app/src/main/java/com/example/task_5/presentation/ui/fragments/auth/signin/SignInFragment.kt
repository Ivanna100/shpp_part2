package com.example.task_5.presentation.ui.fragments.auth.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.task_5.databinding.FragmentSignInBinding
import com.example.task_5.data.model.UserRequest
import com.example.task_5.data.model.UserWithTokens
import com.example.task_5.domain.state.UserApiResultState
import com.example.task_5.presentation.ui.fragments.BaseFragment
import com.example.task_5.utils.DataStore.saveData
import com.example.task_5.utils.ext.invisible
import com.example.task_5.utils.ext.showErrorSnackBar
import com.example.task_5.utils.ext.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInFragment : BaseFragment<FragmentSignInBinding> (FragmentSignInBinding::inflate) {

    private val viewModel : SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObserver()
    }

    private fun setListeners() {
        logIn()
        signUp()
    }

    private fun logIn() {
        with(binding) {
            buttonLoginSignIn.setOnClickListener{
                viewModel.authorizationUser(
                    UserRequest(
                        textInputEditTextEmailSignIn.text.toString(),
                        textInputEditTextPasswordSignIn.text.toString()
                    )
                )
            }
        }
    }

    private fun signUp() {
        binding.textViewSignUpSignIn.setOnClickListener {
            val direction = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            navController.navigate(direction)
        }
    }

    private fun setObserver() {
        lifecycleScope.launch {
            viewModel.authorizationState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect{
                when(it) {
                    is UserApiResultState.Success -> {
                        if(binding.checkboxRememberMeSignIn.isChecked) {
                            lifecycleScope.launch(Dispatchers.IO) {
                                saveData(
                                    requireContext(),
                                    binding.textInputEditTextEmailSignIn.text.toString(),
                                    binding.textInputEditTextPasswordSignIn.text.toString()
                                )
                            }
                        }
                        val direction = SignInFragmentDirections.actionSignInFragmentToViewPagerFragment(
                            UserWithTokens(
                                it.data.user,
                                it.data.accessToken,
                                it.data.refreshToken
                            )
                        )
                        navController.navigate(direction)
                    }

                    is UserApiResultState.Initial -> Unit

                    is UserApiResultState.Loading -> {
                        binding.progressBar.visible()
                    }

                    is UserApiResultState.Error -> {
                        binding.progressBar.invisible()
                        binding.root.showErrorSnackBar(requireContext(), it.error)
                    }
                }
            }
        }
    }
}