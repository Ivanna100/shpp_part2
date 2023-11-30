package com.example.task_5.data.model

data class ResponceOfUsers (
    val status : String,
    val code : Int,
    val message : String? = null,
    val data : Data
        ) { data class Data ( val users : ArrayList<UserData>?)}