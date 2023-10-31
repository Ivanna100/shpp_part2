package com.example.task_4.utils

object Constants {
    const val DIALOG_TAG = "add_contact_dialog"
    const val LOG_TAG = "LOG_TAG"
    const val TRANSITION_NAME_IMAGE = "TRANSITION_NAME_IMAGE"
    const val TRANSITION_NAME_NAME = "TRANSITION_NAME_FULL_NAME"
    const val TRANSITION_NAME_CAREER = "TRANSITION_NAME_CAREER"

    // data store
    const val REGISTER_DATA_STORE = "data store"
    const val KEY_EMAIL = "email"
    const val KEY_REMEMBER_ME = "remember me"

    // screens in viewpager
    const val FRAGMENT_COUNT = 2

    // validation
    const val PASSWORD_REGEX =
        "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{7,}\$"
}