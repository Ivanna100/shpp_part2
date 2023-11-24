package com.example.task_3.data.repository

import com.example.task_3.R
import com.example.task_3.data.UserDataHolder
import com.example.task_3.data.model.Contact
import com.example.task_3.data.model.UserRequest
import com.example.task_3.domain.network.ContactsApiService
import com.example.task_3.domain.state.ArrayDataApiResultState
import com.example.task_3.domain.state.UserApiResultState
import com.example.task_3.utils.Constants
import com.example.task_3.utils.Constants.AUTHORIZATION_PREFIX
import com.example.task_3.utils.Constants.OPERATION_ERROR
import retrofit2.http.Body
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsRepositoryImpl @Inject constructor(private val service: ContactsApiService) {

    suspend fun getAllUsers(accessToken: String): ArrayDataApiResultState {
        return try {
            val response = service.getAllUsers("${Constants.AUTHORIZATION_PREFIX}$accessToken")
            response.data.let { ArrayDataApiResultState.Success(it.users) }
        } catch (e: Exception) {
            ArrayDataApiResultState.Error(OPERATION_ERROR)
        }
    }

    suspend fun addContact(
        userId: Long,
        accessToken: String,
        contact: Contact
    ): ArrayDataApiResultState {
        return try {
            val response =
                service.addContact(userId, "${AUTHORIZATION_PREFIX}$accessToken", contact.id)
            response.data.let { ArrayDataApiResultState.Success(it.contacts) }
        } catch (e: Exception) {
            ArrayDataApiResultState.Error(OPERATION_ERROR)
        }
    }

    suspend fun deleteContact(
        userId: Long,
        contact: Contact,
        accessToken: String
    ): ArrayDataApiResultState {
        return try {
            val response = service.deleteContact(
                userId, contact.id,
                "${AUTHORIZATION_PREFIX}$accessToken"
            )
            response.data.let { ArrayDataApiResultState.Success(it.contacts) }
        } catch (e: Exception) {
            ArrayDataApiResultState.Error(OPERATION_ERROR)
        }
    }

    suspend fun getUserContacts(userId: Long, accessToken: String): ArrayDataApiResultState {
        return try {
            val response = service.getUserContacts(userId, "${AUTHORIZATION_PREFIX}$accessToken")
            UserDataHolder.setContactList((response.data.contacts?.map { it.toContact() }
                ?: emptyList()) as ArrayList<Contact>)
            response.data.let { ArrayDataApiResultState.Success(it.contacts) }
        } catch (e: Exception) {
            ArrayDataApiResultState.Error(OPERATION_ERROR)
        }
    }

}
