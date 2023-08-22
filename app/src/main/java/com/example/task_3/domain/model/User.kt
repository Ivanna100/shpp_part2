package com.example.task_3.domain.model

import java.io.Serializable
import java.util.UUID

data class User(
    val name: String,
    val career: String,
    val photo: String = "",
    val id: UUID = UUID.randomUUID()
) : Serializable
