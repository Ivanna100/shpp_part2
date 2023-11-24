package com.example.task_3.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.task_3.R
import com.example.task_3.databinding.FragmentSplashBinding
import com.example.task_3.utils.Constants
import com.example.task_3.utils.DataStoreManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
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