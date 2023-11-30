package com.example.task_5.data.repository

import com.example.task_3.R
import com.example.task_5.data.UserDataHolder
import com.example.task_5.data.model.Contact
import com.example.task_5.data.model.UserData
import com.example.task_5.domain.network.ContactsApiService
import com.example.task_5.domain.repository.ContactRepository
import com.example.task_5.domain.state.ArrayDataApiResultState
import com.example.task_5.utils.Constants
import com.example.task_5.utils.Constants.AUTHORIZATION_PREFIX
import com.example.task_5.utils.Constants.OPERATION_ERROR
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsRepositoryImpl @Inject constructor(private val service: ContactsApiService,
private val repository: ContactRepository) {

    suspend fun getAllUsers(accessToken: String, user : UserData): ArrayDataApiResultState {
        return try {
            val response = service.getAllUsers("${Constants.AUTHORIZATION_PREFIX}$accessToken")
            val contacts = UserDataHolder.getContacts()
            val filteredUsers = response.data.users?.filter {
                it.name != null && it.email != user.email && !contacts.contains(it.toContact())
            }
            val users : MutableStateFlow<List<Contact>> = MutableStateFlow(filteredUsers?.map {
                it.toContact()} ?: emptyList())
            UserDataHolder.setServerList(users)
            response.data.let { ArrayDataApiResultState.Success(it.users) }
        } catch (e: Exception) {
            ArrayDataApiResultState.Error(R.string.invalid_request)
        }
    }

    suspend fun addContact(
        userId: Long,
        accessToken: String,
        contact: Contact
    ): ArrayDataApiResultState {
        val states : ArrayList<Pair<Long, ArrayDataApiResultState>> = ArrayList()
        states.add(Pair(contact.id, ArrayDataApiResultState.Loading))
        return try {
            val response =
                service.addContact(userId, "${AUTHORIZATION_PREFIX}$accessToken", contact.id)
            states[states.size-1] =
                Pair(contact.id, ArrayDataApiResultState.Success(response.data.contacts))
            UserDataHolder.setStates(states[states.size - 1])
            response.data.let { ArrayDataApiResultState.Success(it.contacts) }
        } catch (e: Exception) {
            ArrayDataApiResultState.Error(R.string.invalid_request)
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
            ArrayDataApiResultState.Error(R.string.invalid_request)
        }
    }

    suspend fun getUserContacts(userId: Long, accessToken: String): ArrayDataApiResultState {
        return try {
            val response = service.getUserContacts(userId, "${AUTHORIZATION_PREFIX}$accessToken")
            UserDataHolder.setContactList((response.data.contacts?.map { it.toContact() }
                ?: emptyList()) as ArrayList<Contact>)
            response.data.let { ArrayDataApiResultState.Success(it.contacts) }
        } catch (e: Exception) {
            ArrayDataApiResultState.Error(R.string.invalid_request)
        }
    }

}
