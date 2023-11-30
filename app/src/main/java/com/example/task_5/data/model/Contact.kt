package com.example.task_5.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Contact(
    val name: String? = null,
    val career: String? = null,
    val photo: String? = null,
    val address: String? = null,
    val id: Long = 0
) : Parcelable