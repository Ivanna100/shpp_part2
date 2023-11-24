package com.example.task_3.data.repository

import com.example.task_3.R
import com.example.task_3.data.model.UserRequest
import com.example.task_3.domain.network.AccountApiService
import com.example.task_3.domain.state.ArrayDataApiResultState
import com.example.task_3.domain.state.UserApiResultState
import com.example.task_3.utils.Constants.AUTHORIZATION_PREFIX
import com.example.task_3.utils.Constants.OPERATION_ERROR
import com.example.task_3.utils.ErrorMessage
import retrofit2.http.Body
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepositoryImpl @Inject constructor( private val service: AccountApiService){

    suspend fun registerUser(@Body body : UserRequest) : UserApiResultState {
        return try {
            val response = service.registerUser(body)
            response.data?.let { UserApiResultState.Success(it) } ?:
            UserApiResultState.Error(OPERATION_ERROR)
        } catch (e : Exception) {
            UserApiResultState.Error(OPERATION_ERROR)
        }
    }

    suspend fun authorizeUser(@Body body : UserRequest) : UserApiResultState {
        return try {
            val response = service.authorizeUser(body)
            response.data?.let { UserApiResultState.Success(it) } ?:
            UserApiResultState.Error(OPERATION_ERROR)
        } catch (e : Exception) {
            UserApiResultState.Error(OPERATION_ERROR)
        }
    }

    suspend fun getUser(userId : Long, accessToken : String) : UserApiResultState {
        return try {
            val response = service.getUser(userId, "$AUTHORIZATION_PREFIX$accessToken")
            response.data?.let { UserApiResultState.Success(it) } ?:
            UserApiResultState.Error(ErrorMessage.getErrorMessage(response.code))
        } catch (e : Exception) {
            UserApiResultState.Error(OPERATION_ERROR)
        }
    }

    suspend fun editUser(userId: Long, accessToken: String, name : String, career: String?,
                         phone: String, address: String?, birthday: Date?) : UserApiResultState {
        return try {
            val response = service.editUser(userId, "$AUTHORIZATION_PREFIX$accessToken",
                name, career, phone, address, birthday)
            response.data?.let { UserApiResultState.Success(it) } ?:
            UserApiResultState.Error(OPERATION_ERROR)
        } catch (e : Exception) {
            UserApiResultState.Error(OPERATION_ERROR)
        }
    }

}