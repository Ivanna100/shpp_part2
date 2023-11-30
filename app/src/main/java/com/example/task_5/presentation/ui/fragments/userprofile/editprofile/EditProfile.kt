package com.example.task_5.presentation.ui.fragments.userprofile.editprofile

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.task_5.databinding.FragmentEditProfileBinding
import com.example.task_5.domain.state.UserApiResultState
import com.example.task_5.presentation.ui.fragments.BaseFragment
import com.example.task_5.utils.Constants
import com.example.task_5.utils.Parser
import com.example.task_5.utils.Validation
import com.example.task_5.utils.ext.invisible
import com.example.task_5.utils.ext.loadImage
import com.example.task_5.utils.ext.showErrorSnackBar
import com.example.task_5.utils.ext.visible
import com.example.task_5.utils.ext.visibleIf
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditProfile : BaseFragment<FragmentEditProfileBinding> (FragmentEditProfileBinding::inflate){

    private val viewModel: EditProfileViewModel by viewModels()
    private val args: EditProfileArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObserver()
        setInputs()
        setCalendar()
    }

    private fun setListeners() {
        save()
        inputPhone()
        navigationBack()
    }

    private fun save() {
        with(binding) {
            buttonSave.setOnClickListener {
                if(Validation.isValidUserName(textInputEditTextUserName.text.toString()) &&
                        Validation.isValidMobilePhone(textInputEditTextPhone.text.toString())) {
                    viewModel.editUser(
                        args.userData.user.id,
                        args.userData.accessToken,
                        textInputEditTextUserName.text.toString(),
                        textInputEditTextCareer.text.toString(),
                        textInputEditTextPhone.text.toString(),
                        textInputEditTextAddress.text.toString(),
                        Parser.getDataFromString(textInputEditTextDate.text.toString())
                    )
                }
            }
        }
    }

    private fun inputPhone() {
        binding.textInputEditTextPhone.addTextChangedListener {
            PhoneNumberFormattingTextWatcher(Constants.MOBILE_CODE)
        }
    }

    private fun navigationBack() {
        binding.imageViewNavigationBack.setOnClickListener {
            navController.navigateUp()
        }
    }

    private fun setObserver() {
        lifecycleScope.launch {
            viewModel.editUserState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect{
                when(it) {
                    is UserApiResultState.Success -> {
                        navController.navigateUp()
                    }

                    is UserApiResultState.Initial -> Unit

                    is UserApiResultState.Loading -> {
                        binding.progressBar.visible()
                    }

                    is UserApiResultState.Error -> {
                        binding.root.showErrorSnackBar(requireContext(), it.error)
                        binding.progressBar.invisible()
                    }
                }
            }
        }
    }

    private fun setInputs() {
        inputsErrors()
        with(binding) {
            imageViewSignUpExtendedMockup.invisible()
            imageViewSignUpExtendedPhoto.loadImage(args.userData.user.image)
            textInputEditTextUserName.setText(args.userData.user.name ?: "")
            textInputEditTextCareer.setText(args.userData.user.career ?: "")
            textInputEditTextPhone.setText(args.userData.user.phone ?: "")
            textInputEditTextAddress.setText(args.userData.user.address ?: "")
            textInputEditTextDate.setText(args.userData.user.birthday?.let {
                Parser.getStringFromData(it.toString()) } ?: "")
        }
    }

    private fun inputsErrors() {
        with(binding) {
            textInputEditTextUserName.doOnTextChanged { text, _, _, _ ->
                textViewInvalidUserName.visibleIf(!Validation.isValidUserName(text.toString()))
            }
            textInputEditTextPhone.doOnTextChanged { text, _, _, _ ->
                textViewInvalidPhone.visibleIf(!Validation.isValidMobilePhone(text.toString()))
            }
        }
    }

    private fun setCalendar() {
        with(binding) {
            textInputEditTextDate.setOnClickListener {  }
        }
    }

}