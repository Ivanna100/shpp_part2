package com.example.task_3.ui.recycler_view

import android.view.View
import com.example.task_3.domain.model.User

interface UserItemClickListener {
    fun onUserDelete(contact: User, position: Int)
    fun onOpenNewFragment(contact: User, transitionPairs: Array<Pair<View, String>>)
}
