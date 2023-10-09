package com.example.task_3.ui.fragments.viewpager

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.task_3.databinding.FragmentViewPagerBinding
import com.example.task_3.ui.fragments.BaseFragment
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
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout,viewPager) {
             tab, position -> tab.text = when(position) {
                0 -> "Profile"
                1 -> "Contacts"
                else -> throw IllegalStateException("Unknown tab!")
             }
        }.attach()
        }
    }

    fun openFragment(index: Int) {
        binding.viewPager.currentItem = index
    }
}