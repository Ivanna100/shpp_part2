package com.example.task_3.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.task_3.databinding.FragmentSplashBinding
import com.example.task_3.utils.Constants
import com.example.task_3.utils.DataStoreManager
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isAutoLogin()
    }

    private fun isAutoLogin() {
        lifecycleScope.launch {
            val isRememberUser =
                DataStoreManager.getDataFromKey(requireContext(), Constants.KEY_REMEMBER_ME)
            if(isRememberUser != null) {
                val email = DataStoreManager.getDataFromKey(requireContext(), Constants.KEY_EMAIL)
                val direction =
                    SplashFragmentDirections.actionSplashFragmentToViewPagerFragment(email)
                navController.navigate(direction)
            } else {
                val direction = SplashFragmentDirections.actionSplashFragmentToAuthFragment()
                navController.navigate(direction)
            }
        }
    }

}