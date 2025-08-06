package com.hazratbilal.notecraft.compose.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hazratbilal.notecraft.compose.model.UserRequest
import com.hazratbilal.notecraft.compose.model.UserResponse
import com.hazratbilal.notecraft.compose.remote.NetworkResult
import com.hazratbilal.notecraft.compose.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _userResponseStateFlow = MutableStateFlow<NetworkResult<UserResponse>>(NetworkResult.Idle())
    val userResponseStateFlow: StateFlow<NetworkResult<UserResponse>> get() = _userResponseStateFlow

    fun clearState() {
        _userResponseStateFlow.value = NetworkResult.Idle()
    }

    fun registerUser(userRequest: UserRequest) {
        viewModelScope.launch {
            _userResponseStateFlow.value = NetworkResult.Loading()
            val result = userRepository.registerUser(userRequest)
            _userResponseStateFlow.value = result
        }
    }

    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            _userResponseStateFlow.value = NetworkResult.Loading()
            val result = userRepository.loginUser(userRequest)
            _userResponseStateFlow.value = result
        }
    }

    fun updateProfile(userRequest: UserRequest) {
        viewModelScope.launch {
            _userResponseStateFlow.value = NetworkResult.Loading()
            val result = userRepository.updateProfile(userRequest)
            _userResponseStateFlow.value = result
        }
    }

    fun changePassword(userRequest: UserRequest) {
        viewModelScope.launch {
            _userResponseStateFlow.value = NetworkResult.Loading()
            val result = userRepository.changePassword(userRequest)
            _userResponseStateFlow.value = result
        }
    }

}
