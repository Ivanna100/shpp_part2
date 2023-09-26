package com.example.task_3.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.task_3.domain.model.User

class MyDiffUtil : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id
}