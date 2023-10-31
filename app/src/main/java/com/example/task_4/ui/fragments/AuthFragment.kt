package com.example.task_4.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.task_4.databinding.FragmentAuthBinding
import com.example.task_4.utils.Constants
import com.example.task_4.utils.DataStoreManager
import com.example.task_4.utils.Validation
import com.example.task_4.utils.ext.invisible
import com.example.task_4.utils.ext.visibleIf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO use base fragment
class AuthFragment : Fragment() {

    //TODO memory leak, in fragment you should null binding in onDestroyView
    private lateinit var binding: FragmentAuthBinding


    //TODO move logic to onViewCreated
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        setListeners()
        dataValidation()
        return binding.root
    }


    //TODO extract validation logic to viewModel
    private fun setListeners() {
        with(binding) {
            buttonRegister.setOnClickListener {
                if(Validation.isValidEmail(textInputEditTextEmail.text.toString()) &&
                        Validation.isValidPassword(textInputEditTextPassword.text.toString())) {

                    if(checkboxRemember.isChecked) saveData()
                    val direction = AuthFragmentDirections.actionAuthFragmentToViewPagerFragment(
                        textInputEditTextEmail.text.toString()
                    )
                    findNavController().navigate(direction)
                }
            }
        }
    }

    //TODO extract saving data to data layer
    private fun saveData() {
        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreManager.putData(
                requireContext(),
                Constants.KEY_EMAIL,
                binding.textInputEditTextEmail.text.toString()
            )
            DataStoreManager.putData(
                requireContext(),
                Constants.KEY_REMEMBER_ME,
                Constants.KEY_REMEMBER_ME
            )
        }
    }

    private fun dataValidation() {
        with(binding) {
            textInputEditTextEmail.doOnTextChanged { text, _, _, _ ->
                textViewInvalidEmail.visibleIf(
                    !Validation.isValidEmail(text.toString()) && !text.isNullOrEmpty()
                )
            }
            textInputEditTextPassword.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrEmpty()) {
                    textViewInvalidPassword.visibility =
                        if (!Validation.isValidPassword(text.toString())) View.VISIBLE else View.INVISIBLE
                } else {
                    textViewInvalidPassword.invisible()
                }
            }
        }
    }
}