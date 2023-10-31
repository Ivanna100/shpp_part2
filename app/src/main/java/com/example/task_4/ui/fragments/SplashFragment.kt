package com.example.task_4.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.task_4.databinding.FragmentSplashBinding
import com.example.task_4.utils.Constants
import com.example.task_4.utils.DataStoreManager
import kotlinx.coroutines.launch

//TODO you should not save data in DataStore in this fragment, extract this logic to data layer
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