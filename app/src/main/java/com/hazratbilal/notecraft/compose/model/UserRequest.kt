package com.hazratbilal.notecraft.compose.model

data class UserRequest (
    val full_name: String,
    val email: String,
    val gender: String,
    val password: String,
    val new_password: String
)