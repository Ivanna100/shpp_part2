package com.example.task_3.ui.recycler_view

import android.view.View
import com.example.task_3.domain.model.User

interface UserItemClickListener {
    fun onUserDeleteClick(contact: User, position: Int)
    fun onUserClick(contact: User, transitionPairs: Array<Pair<View, String>>)
}
