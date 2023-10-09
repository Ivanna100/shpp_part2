package com.example.task_3.utils

import android.util.Patterns

class Validation {

    fun isEmailValid(email: String) : Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordValid(password: String) : Boolean =
        Regex(Constants.PASSWORD_REGEX).matches(password) && !password.contains(" ")
}