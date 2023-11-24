package com.example.task_3.domain.state

import com.example.task_3.data.model.ResponseOfUser

sealed class UserApiResultState {

    data class Success( val data : ResponseOfUser.Data) : UserApiResultState()

    data class Error(val error: String) : UserApiResultState()

    object Loading : UserApiResultState()
}