package com.example.task_5.presentation.ui.fragments.viewpager

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.task_5.databinding.FragmentViewPagerBinding
import com.example.task_5.presentation.ui.fragments.BaseFragment
import com.example.task_5.presentation.ui.fragments.viewpager.adapter.ViewPagerAdapter
import com.example.task_5.utils.Constants
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.IllegalStateException

class ViewPagerFragment :
    BaseFragment<FragmentViewPagerBinding>(FragmentViewPagerBinding::inflate) {

    private val args: ViewPagerFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(this@ViewPagerFragment, args)
        with(binding) {
            viewPager.offscreenPageLimit = 1
            viewPager.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    Constants.FIRST_FRAGMENT -> "Profile"
                    Constants.SECOND_FRAGMENT -> "Contacts"
                    else -> throw IllegalStateException("Unknown tab!")
                }
            }.attach()
        }
    }

    fun openFragment(index: Int) {
        binding.viewPager.currentItem = index
    }
}