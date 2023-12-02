package com.example.task_5.domain.repository

import com.example.task_5.data.model.Contact
import com.example.task_5.data.model.UserData
import com.example.task_5.domain.state.ArrayDataApiResultState

interface ContactsRepository {

    suspend fun addContact(
        userId: Long,
        accessToken: String,
        contact: Contact
    ): ArrayDataApiResultState

    suspend fun getUserContacts(userId: Long, accessToken: String): ArrayDataApiResultState

    suspend fun getAllUsers(accessToken: String, user: UserData): ArrayDataApiResultState
    suspend fun deleteContact(
        userId: Long,
        accessToken: String,
        contactId: Long
    ): ArrayDataApiResultState
}