package com.example.task_3.domain.model

import java.io.Serializable
import java.util.UUID

data class Contact(
    val name: String,
    val career: String,
    val photo: String = "",
    val id: UUID = UUID.randomUUID(),
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val date: String = "",
    var isChecked: Boolean = false
) : Serializable
