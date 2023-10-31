package com.example.task_4.ui.fragments.viewpager

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.task_4.databinding.FragmentViewPagerBinding
import com.example.task_4.ui.fragments.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

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
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout,viewPager) {
             tab, position -> tab.text = when(ViewPagerAdapter.Fragments.values()[position]) {
                ViewPagerAdapter.Fragments.CONTACTS -> "Profile"
                ViewPagerAdapter.Fragments.USER_PROFILE -> "Contacts"
             }
        }.attach()
        }
    }

    fun openFragment(index: Int) {
        binding.viewPager.currentItem = index
    }
}