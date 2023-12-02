package com.example.task_5.presentation.ui.fragments.auth.signup

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.example.task_5.databinding.FragmentSignUpBinding
import com.example.task_5.presentation.ui.fragments.BaseFragment
import com.example.task_5.utils.Validation
import com.example.task_5.utils.ext.visibleIf

class SignUpFragment : BaseFragment<FragmentSignUpBinding> (FragmentSignUpBinding::inflate){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        dataValidation()
    }

    private fun setListeners() {
        register()
        signIn()
    }

    private fun register() {
        with(binding) {
            buttonRegister.setOnClickListener {
                if(Validation.isValidEmail(textInputEditTextEmail.text.toString()) &&
                        Validation.isValidPassword(textInputEditTextPassword.text.toString())) {
                    val direction = SignUpFragmentDirections.actionSignUpFragmentToSignUpExtendedFragment(
                        textInputEditTextEmail.text.toString(),
                        textInputEditTextPassword.text.toString(),
                        checkboxRemember.isChecked
                    )
                    navController.navigate(direction)
                }
            }
        }
    }

    private fun signIn() {
        binding.textViewSignUp.setOnClickListener {
            navController.navigateUp()
        }
    }

    private fun dataValidation() {
        with(binding) {
            textInputEditTextEmail.doOnTextChanged{ text, _, _, _ ->
                textViewInvalidEmail.visibleIf(!Validation.isValidEmail(text.toString()) &&
                !text.isNullOrEmpty())
            }
            textInputEditTextPassword.doOnTextChanged { text, _, _, _ ->
                textViewInvalidEmail.visibleIf(!Validation.isValidPassword(text.toString()) &&
                !text.isNullOrEmpty())
            }
        }
    }
}