package com.example.task_5.domain.repository

import com.example.task_5.data.model.Contact
import com.example.task_5.domain.state.ArrayDataApiResultState

interface ContactRepository {

    suspend fun getAllUsers(accessToken: String): ArrayDataApiResultState

    suspend fun addContact(
        userId: Long,
        accessToken: String,
        contact: Contact
    ): ArrayDataApiResultState

    suspend fun deleteContact(
        userId: Long,
        contact: Contact,
        accessToken: String
    ): ArrayDataApiResultState

    suspend fun getUserContacts(userId: Long, accessToken: String): ArrayDataApiResultState

}