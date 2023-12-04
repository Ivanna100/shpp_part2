package com.example.task_5.data.repository

import com.example.task_5.R
import com.example.task_5.data.model.UserRequest
import com.example.task_5.data.api.AccountApiService
import com.example.task_5.domain.repository.AccountRepository
import com.example.task_5.domain.state.UserApiResultState
import com.example.task_5.utils.Constants.AUTHORIZATION_PREFIX
import retrofit2.http.Body
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepositoryImpl @Inject constructor(
   private val service: AccountApiService,
//   private val repository : AccountRepository
) : AccountRepository {

    override suspend fun registerUser(@Body body : UserRequest) : UserApiResultState {
        return try {
            val response = service.registerUser(body)
            response.data?.let { UserApiResultState.Success(it) } ?:
            UserApiResultState.Error(R.string.invalid_request)
        } catch (e : Exception) {
            UserApiResultState.Error(R.string.register_error_user_exist)
        }
    }

    override suspend fun authorizeUser(@Body body : UserRequest) : UserApiResultState {
        return try {
            val response = service.authorizeUser(body)
            response.data?.let { UserApiResultState.Success(it) } ?:
            UserApiResultState.Error(R.string.invalid_request)
        } catch (e : Exception) {
            UserApiResultState.Error(R.string.not_correct_input)
        }
    }

    override suspend fun getUser(userId : Long, accessToken : String) : UserApiResultState {
        return try {
            val response = service.getUser(userId, "$AUTHORIZATION_PREFIX$accessToken")
            response.data?.let { UserApiResultState.Success(it) } ?:
            UserApiResultState.Error(R.string.invalid_request)
        } catch (e : Exception) {
            UserApiResultState.Error(R.string.invalid_request)
        }
    }

    override suspend fun editUser(userId: Long, accessToken: String, name : String, career: String?,
                                  phone: String, address: String?, birthday: Date?) : UserApiResultState {
        return try {
            val response = service.editUser(userId, "$AUTHORIZATION_PREFIX$accessToken",
                name, career, phone, address, birthday)
            response.data?.let { UserApiResultState.Success(it) } ?:
            UserApiResultState.Error(R.string.invalid_request)
        } catch (e : Exception) {
            UserApiResultState.Error(R.string.invalid_request)
        }
    }

    override suspend fun autoLogin(email : String, password : String) : UserApiResultState {
        return try {
            val response = service.authorizeUser(
                UserRequest(
                    email = email,
                    password = password
                )
            )
            response.data?.let { UserApiResultState.Success(it) } ?: UserApiResultState.Error(
                R.string.invalid_request
            )
        } catch (e : Exception) {
            UserApiResultState.Error(R.string.automatic_login_error)
        }
    }

}