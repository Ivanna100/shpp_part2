package com.example.task_5.domain.repository

import com.example.task_5.data.model.UserRequest
import com.example.task_5.domain.state.UserApiResultState
import java.util.Date

interface AccountRepository {

    suspend fun registerUser(body : UserRequest) : UserApiResultState

    suspend fun authorizeUser(body : UserRequest) : UserApiResultState

    suspend fun getUser(userId : Long, accessToken : String) : UserApiResultState

    suspend fun editUser(userId: Long, accessToken: String, name : String, career: String?,
                         phone: String, address: String?, birthday: Date?) : UserApiResultState

    suspend fun autoLogin(email : String, password : String) : UserApiResultState

}