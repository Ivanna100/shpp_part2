package com.example.task_5.presentation.ui.fragments.auth.signup.signupextended

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task_5.data.model.UserRequest
import com.example.task_5.data.repository.AccountRepositoryImpl
import com.example.task_5.domain.repository.AccountRepository
import com.example.task_5.domain.state.UserApiResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpExtendedViewModel @Inject constructor( private val accountRepImpl : AccountRepositoryImpl)
    : ViewModel() {

        private val _registerStateFlow = MutableStateFlow<UserApiResultState>(UserApiResultState.Initial)
        val registerState: StateFlow<UserApiResultState> = _registerStateFlow

        fun isLogout() {
            _registerStateFlow.value = UserApiResultState.Initial
        }

        fun registerUser(body: UserRequest) = viewModelScope.launch(Dispatchers.IO) {
            _registerStateFlow.value = UserApiResultState.Loading
            _registerStateFlow.value = accountRepImpl.registerUser(body)
        }

    }