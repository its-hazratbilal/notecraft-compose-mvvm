package com.hazratbilal.notecraft.compose.repository

import com.hazratbilal.notecraft.compose.model.UserRequest
import com.hazratbilal.notecraft.compose.model.UserResponse
import com.hazratbilal.notecraft.compose.remote.NetworkResult
import com.hazratbilal.notecraft.compose.remote.UserAPI
import com.hazratbilal.notecraft.compose.remote.safeApiCall
import javax.inject.Inject


class UserRepository @Inject constructor(private val userAPI: UserAPI) {

    suspend fun registerUser(userRequest: UserRequest): NetworkResult<UserResponse> {
        return safeApiCall { userAPI.signUp(userRequest) }
    }

    suspend fun loginUser(userRequest: UserRequest): NetworkResult<UserResponse> {
        return safeApiCall { userAPI.signIn(userRequest) }
    }

    suspend fun updateProfile(userRequest: UserRequest): NetworkResult<UserResponse> {
        return safeApiCall { userAPI.updateProfile(userRequest) }
    }

    suspend fun changePassword(userRequest: UserRequest): NetworkResult<UserResponse> {
        return safeApiCall { userAPI.changePassword(userRequest) }
    }

}
