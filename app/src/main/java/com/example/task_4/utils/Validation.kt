package com.example.task_4.utils

import android.util.Patterns

object Validation {

    fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String): Boolean =
        Regex(Constants.PASSWORD_REGEX).matches(password) && !password.contains(" ")

}