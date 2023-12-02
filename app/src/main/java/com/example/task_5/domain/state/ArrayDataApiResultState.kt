package com.example.task_5.domain.state

import com.example.task_5.data.model.UserData

sealed class ArrayDataApiResultState {

    object Initial : ArrayDataApiResultState()

    data class Success( val data : ArrayList<UserData>?) : ArrayDataApiResultState()

    data class Error (val error : Int) : ArrayDataApiResultState()

    object Loading : ArrayDataApiResultState()
}