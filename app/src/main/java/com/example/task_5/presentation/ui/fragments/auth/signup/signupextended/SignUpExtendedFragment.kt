package com.example.task_5.presentation.ui.fragments.auth.signup.signupextended

import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.task_5.R
import com.example.task_5.databinding.SignUpExtendedBinding
import com.example.task_5.data.model.UserRequest
import com.example.task_5.data.model.UserWithTokens
import com.example.task_5.domain.state.UserApiResultState
import com.example.task_5.presentation.ui.fragments.BaseFragment
import com.example.task_5.utils.Constants
import com.example.task_5.utils.DataStore.saveData
import com.example.task_5.utils.Validation
import com.example.task_5.utils.ext.invisible
import com.example.task_5.utils.ext.loadImage
import com.example.task_5.utils.ext.showErrorSnackBar
import com.example.task_5.utils.ext.visible
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignUpExtendedFragment : BaseFragment<SignUpExtendedBinding> (SignUpExtendedBinding::inflate){

    private val viewModel: SignUpExtendedViewModel by viewModels()

    private val args : SignUpExtendedFragmentArgs by navArgs()

    private var photoUri: Uri? = null
    private val requestImageLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {uri ->
            uri?.let {
                photoUri = it
                binding.imageViewSignUpPhotoSue.loadImage(it.toString())
                binding.imageViewSignUpMockupSue.visibility = View.GONE
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setSignUpExtended()
        setObservers()
    }

    private fun setListeners() {
        cancel()
        forward()
        setPhoto()
        inputMobilePhone()
    }

    private fun cancel() {
        binding.buttonCancelSue.setOnClickListener {
            navController.navigateUp()
        }
    }

    private fun forward() {
        with(binding) {
            buttonForwardSue.setOnClickListener {
                if(!Validation.isValidUserName(textInputEditTextUserNameSue.text.toString())) {
                    root.showErrorSnackBar(requireContext(), R.string.user_name_must_contain_at_least_3_letters)
                } else if(!Validation.isValidMobilePhone(textInputEditTextMobilePhoneSue.text.toString())) {
                    root.showErrorSnackBar(requireContext(), R.string.phone_must_be_at_least_10_digits_long)
                } else {
                    viewModel.registerUser(
                        UserRequest(
                            args.email,
                            args.password,
                            textInputEditTextUserNameSue.text.toString(),
                            textInputEditTextMobilePhoneSue.text.toString()
                        )
                    )
                }
            }
        }
    }

    private fun setPhoto() {
        binding.imageViewAddPhotoSue.setOnClickListener {
        val request = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            requestImageLauncher.launch(request)
        }
    }

    private fun inputMobilePhone() {
        binding.textInputEditTextMobilePhoneSue.addTextChangedListener(
            PhoneNumberFormattingTextWatcher(Constants.MOBILE_CODE)
        )
    }

    private fun setSignUpExtended() {
        binding.textInputEditTextUserNameSue.setText(parsingEmail(args.email))
    }

    private fun parsingEmail(email : String) : String {
        val elements = email.split("@")[0].replace(".", " ").split(" ")
        return if(elements.size >= 2) {
            "${elements[0].replaceFirstChar { it.uppercase() }} ${elements[1].replaceFirstChar { it.titlecase() }}"
        } else elements[0]
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.registerState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect{
                when(it) {
                    is UserApiResultState.Success -> {
                        if(args.rememberMe) saveData(requireContext(), args.email, args.password)
                        viewModel.isLogout()
                            val direction = SignUpExtendedFragmentDirections.actionSignUpExtendedFragmentToViewPagerFragment(
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
                        binding.progressBarSue.visible()
                    }

                    is UserApiResultState.Error -> {
                        binding.root.showErrorSnackBar(requireContext(), it.error)
                        viewModel.isLogout()
                        binding.progressBarSue.invisible()
                    }
                }
            }
        }
    }
}