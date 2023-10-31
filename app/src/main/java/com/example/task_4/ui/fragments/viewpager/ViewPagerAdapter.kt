package com.example.task_4.ui.fragments.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task_4.ui.fragments.UserProfile
import com.example.task_4.ui.fragments.contacts.ContactsFragment
import com.example.task_4.utils.Constants

class ViewPagerAdapter(fragment: Fragment, private val args: ViewPagerFragmentArgs)
    : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = Fragments.values().size

    override fun createFragment(position: Int): Fragment {
        return when (Fragments.values()[position]) {
            Fragments.USER_PROFILE -> {
                val userProfileFragment = UserProfile()
                userProfileFragment.arguments = args.toBundle()
                userProfileFragment
            }
            Fragments.CONTACTS -> ContactsFragment()
        }
    }

    enum class Fragments{
        USER_PROFILE,
        CONTACTS
    }
}