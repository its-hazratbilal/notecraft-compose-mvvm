package com.hazratbilal.notecraft.compose.ui.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hazratbilal.notecraft.compose.model.UserRequest
import com.hazratbilal.notecraft.compose.remote.NetworkResult
import com.hazratbilal.notecraft.compose.theme.DefaultBackground
import com.hazratbilal.notecraft.compose.ui.components.CustomButton
import com.hazratbilal.notecraft.compose.ui.components.CustomLabel
import com.hazratbilal.notecraft.compose.ui.components.CustomPasswordField
import com.hazratbilal.notecraft.compose.ui.components.Loading
import com.hazratbilal.notecraft.compose.utils.SharedPrefs
import com.hazratbilal.notecraft.compose.utils.sdp
import com.hazratbilal.notecraft.compose.utils.showToast


@Composable
fun ChangePasswordScreen(navController: NavHostController, sharedPrefs: SharedPrefs) {

    val context = LocalContext.current

    val userViewModel: UserViewModel = hiltViewModel()
    val userResponse by userViewModel.userResponseStateFlow.collectAsState()

    var oldPassword by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmNewPassword by rememberSaveable { mutableStateOf("") }

    Loading(isLoading = userResponse is NetworkResult.Loading)

    LaunchedEffect(userResponse) {
        when (userResponse) {
            is NetworkResult.Success -> {
                val userData = userResponse.data
                if (userData?.success == true) {

                    context.showToast("Password updated successfully")
                    navController.popBackStack()

                } else {
                    context.showToast(userData?.message ?: "Unknown error")
                }
            }

            is NetworkResult.AuthError -> {
                context.showToast("Authentication failed, please login again")

                sharedPrefs.clearAll()
                navController.navigate("login") {
                    popUpTo("login") { inclusive = true }
                }
            }

            is NetworkResult.Error -> {
                context.showToast(userResponse.message ?: "Unknown error")
            }

            else -> Unit
        }

        if (userResponse !is NetworkResult.Idle && userResponse !is NetworkResult.Loading) {
            userViewModel.clearState()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(DefaultBackground)
            .padding(all = 20.sdp),
        horizontalAlignment = Alignment.Start
    ) {

        Spacer(modifier = Modifier.height(2.sdp))
        CustomLabel("Old Password")
        Spacer(modifier = Modifier.height(4.sdp))
        CustomPasswordField(hint = "Old Password", value = oldPassword, onValueChange = { oldPassword = it })

        Spacer(modifier = Modifier.height(16.sdp))
        CustomLabel("New Password")
        Spacer(modifier = Modifier.height(4.sdp))
        CustomPasswordField(hint = "New Password", value = newPassword, onValueChange = { newPassword = it })

        Spacer(modifier = Modifier.height(16.sdp))
        CustomLabel("Confirm New Password")
        Spacer(modifier = Modifier.height(4.sdp))
        CustomPasswordField(hint = "Confirm New Password", value = confirmNewPassword, onValueChange = { confirmNewPassword = it })

        Spacer(modifier = Modifier.height(26.sdp))
        CustomButton("Update Password") {
            if (oldPassword.isBlank()) {
                context.showToast("Old password cannot be empty")
            } else if (newPassword.isBlank()) {
                context.showToast("New password cannot be empty")
            } else if (confirmNewPassword.isBlank()) {
                context.showToast("Confirm new password cannot be empty")
            } else if (newPassword != confirmNewPassword) {
                context.showToast("New password and confirm new password do not match")
            } else {
                userViewModel.changePassword(UserRequest("", "", "", oldPassword, newPassword))
            }
        }

        Spacer(modifier = Modifier.height(24.sdp))

    }
}
