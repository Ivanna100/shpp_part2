package com.example.task_5.presentation.ui.fragments.viewpager.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task_5.presentation.ui.fragments.userprofile.UserProfile
import com.example.task_5.presentation.ui.fragments.contacts.ContactsFragment
import com.example.task_5.presentation.ui.fragments.viewpager.ViewPagerFragmentArgs
import com.example.task_5.utils.Constants

class ViewPagerAdapter(fragment: Fragment, private val args: ViewPagerFragmentArgs)
    : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = Constants.FRAGMENT_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (Fragments.values()[position]) {
            Fragments.USER_PROFILE -> {
                val userProfileFragment = UserProfile()
                userProfileFragment.arguments = args.toBundle()
                userProfileFragment
            }
            Fragments.CONTACTS ->  {
                val contactsFragment = ContactsFragment()
                contactsFragment.arguments = args.toBundle()
                contactsFragment
            }
        }
    }

    enum class Fragments(){
        USER_PROFILE,
        CONTACTS
    }
}