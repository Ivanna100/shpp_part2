package com.example.task_3.ui.recycler_view

import androidx.lifecycle.ViewModel
import com.example.task_3.domain.localuserdataset.LocalUserData
import com.example.task_3.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    init {
        _users.value = LocalUserData().getLocalContactsList()
    }

    fun getUsersList(): List<User> = users.value

    fun deleteUser(user: User): Boolean {
        val usersCopy = _users.value.toMutableList()
        if (usersCopy.contains(user)) {
            usersCopy.remove(user)
            _users.value = usersCopy
            return true
        }
        return false
    }

    fun addUser(user: User, position: Int): Boolean {
        val usersCopy = _users.value.toMutableList()
        if (!usersCopy.contains(user)) {
            usersCopy.add(position, user)
            _users.value = usersCopy
            return true
        }
        return false
    }
}