package com.hazratbilal.notecraft.compose.ui.users

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hazratbilal.notecraft.compose.model.UserRequest
import com.hazratbilal.notecraft.compose.remote.NetworkResult
import com.hazratbilal.notecraft.compose.theme.BlackTextColor
import com.hazratbilal.notecraft.compose.theme.DefaultBackground
import com.hazratbilal.notecraft.compose.theme.EditTextBorder
import com.hazratbilal.notecraft.compose.theme.WhiteColor
import com.hazratbilal.notecraft.compose.ui.components.CustomButton
import com.hazratbilal.notecraft.compose.ui.components.CustomLabel
import com.hazratbilal.notecraft.compose.ui.components.CustomTextField
import com.hazratbilal.notecraft.compose.ui.components.Loading
import com.hazratbilal.notecraft.compose.utils.Constant
import com.hazratbilal.notecraft.compose.utils.SharedPrefs
import com.hazratbilal.notecraft.compose.utils.sdp
import com.hazratbilal.notecraft.compose.utils.showToast
import com.hazratbilal.notecraft.compose.utils.ssp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavHostController, sharedPrefs: SharedPrefs) {

    val context = LocalContext.current

    val userViewModel: UserViewModel = hiltViewModel()
    val userResponse by userViewModel.userResponseStateFlow.collectAsState()


    var fullName by rememberSaveable { mutableStateOf(sharedPrefs.getString(Constant.FULL_NAME)) }
    val genderOptions = listOf("male", "female", "other")
    var genderIndex by rememberSaveable { mutableStateOf(genderOptions.indexOf(sharedPrefs.getString(Constant.GENDER))) }
    var gender by rememberSaveable { mutableStateOf(genderOptions[genderIndex]) }
    var expanded by remember { mutableStateOf(false) }

    Loading(isLoading = userResponse is NetworkResult.Loading)

    LaunchedEffect(userResponse) {
        when (userResponse) {
            is NetworkResult.Success -> {
                val userData = userResponse.data
                if (userData?.success == true) {

                    sharedPrefs.putString(Constant.FULL_NAME, fullName)
                    sharedPrefs.putString(Constant.GENDER, gender)
					
                    context.showToast("Profile updated successfully")

                    navController.previousBackStackEntry?.savedStateHandle?.apply {
                        set("profileUpdated", true)
                    }
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
        CustomLabel("Full Name")
        Spacer(modifier = Modifier.height(4.sdp))
        CustomTextField(hint = "Full Name", value = fullName, onValueChange = { fullName = it })

        Spacer(modifier = Modifier.height(16.sdp))
        CustomLabel("Gender")
        Spacer(modifier = Modifier.height(4.sdp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = gender,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.sdp)
                    .background(WhiteColor)
                    .border(
                        width = 1.sdp,
                        color = EditTextBorder,
                        shape = RoundedCornerShape(4.sdp)
                    )
                    .menuAnchor(type = MenuAnchorType.PrimaryNotEditable),
                textStyle = TextStyle(
                    fontSize = 12.ssp,
                    color = BlackTextColor
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(DefaultBackground)
            ) {
                genderOptions.forEach { value ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = value,
                                style = TextStyle(
                                    fontSize = 12.ssp,
                                    color = BlackTextColor
                                )
                            )
                        },
                        onClick = {
                            gender = value
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(26.sdp))
        CustomButton("Update Profile") {
            userViewModel.updateProfile(UserRequest(fullName, "", gender, "", ""))
        }

        Spacer(modifier = Modifier.height(24.sdp))
    }
}
