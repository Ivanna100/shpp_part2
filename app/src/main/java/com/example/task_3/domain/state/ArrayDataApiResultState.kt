package com.example.task_3.domain.state

import com.example.task_3.data.model.UserData

sealed class ArrayDataApiResultState {

//    object Initial : ArrayDataApiResultState()

    data class Success( val data : ArrayList<UserData>?) : ArrayDataApiResultState()

    data class Error (val error : String) : ArrayDataApiResultState()

    object Loading : ArrayDataApiResultState()
}