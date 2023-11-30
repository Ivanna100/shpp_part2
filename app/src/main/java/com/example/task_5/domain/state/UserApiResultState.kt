package com.example.task_5.domain.state

import com.example.task_5.data.model.ResponseOfUser

sealed class UserApiResultState {

    object Initial : UserApiResultState()
    data class Success( val data : ResponseOfUser.Data) : UserApiResultState()

    data class Error(val error: Int) : UserApiResultState()

    object Loading : UserApiResultState()
}