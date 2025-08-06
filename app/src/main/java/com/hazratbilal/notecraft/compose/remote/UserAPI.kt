package com.hazratbilal.notecraft.compose.remote

import com.hazratbilal.notecraft.compose.model.UserRequest
import com.hazratbilal.notecraft.compose.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface UserAPI {

    @POST("register")
    suspend fun signUp(@Body userRequest: UserRequest): Response<UserResponse>

    @POST("login")
    suspend fun signIn(@Body userRequest: UserRequest): Response<UserResponse>

    @POST("change_password")
    @Headers("Require-Auth: true")
    suspend fun changePassword(@Body userRequest: UserRequest): Response<UserResponse>

    @POST("update_profile")
    @Headers("Require-Auth: true")
    suspend fun updateProfile(@Body userRequest: UserRequest): Response<UserResponse>


}