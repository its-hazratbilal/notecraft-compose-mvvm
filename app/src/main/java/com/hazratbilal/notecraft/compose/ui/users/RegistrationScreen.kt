package com.hazratbilal.notecraft.compose.ui.users

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hazratbilal.notecraft.compose.model.UserRequest
import com.hazratbilal.notecraft.compose.remote.NetworkResult
import com.hazratbilal.notecraft.compose.theme.BlackTextColor
import com.hazratbilal.notecraft.compose.theme.DefaultBackground
import com.hazratbilal.notecraft.compose.theme.DefaultColor
import com.hazratbilal.notecraft.compose.theme.EditTextBorder
import com.hazratbilal.notecraft.compose.theme.WhiteColor
import com.hazratbilal.notecraft.compose.ui.components.CustomButton
import com.hazratbilal.notecraft.compose.ui.components.CustomLabel
import com.hazratbilal.notecraft.compose.ui.components.CustomPasswordField
import com.hazratbilal.notecraft.compose.ui.components.CustomTextField
import com.hazratbilal.notecraft.compose.ui.components.Loading
import com.hazratbilal.notecraft.compose.utils.Helper.Companion.isValidEmail
import com.hazratbilal.notecraft.compose.utils.sdp
import com.hazratbilal.notecraft.compose.utils.showToast
import com.hazratbilal.notecraft.compose.utils.ssp
import com.hazratbilal.notecraft.compose.R
import com.hazratbilal.notecraft.compose.ui.components.LoadImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(navController: NavHostController) {

    val context = LocalContext.current

    val userViewModel: UserViewModel = hiltViewModel()
    val userResponse by userViewModel.userResponseStateFlow.collectAsState()

    var fullName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("male") }

    var expanded by remember { mutableStateOf(false) }
    val genderOptions = listOf("male", "female", "other")

    Loading(isLoading = userResponse is NetworkResult.Loading)

    LaunchedEffect(userResponse) {
        when (userResponse) {
            is NetworkResult.Success -> {
                val userData = userResponse.data
                if (userData?.success == true) {

                    context.showToast("Registration successful. Please login")
                    navController.popBackStack()
                } else {
                    context.showToast(userData?.message ?: "Unknown error")
                }
            }

            is NetworkResult.AuthError -> { }

            is NetworkResult.Error -> {
                context.showToast(userResponse.message ?: "Unknown error")
            }

            else -> Unit
        }

        if (userResponse !is NetworkResult.Idle && userResponse !is NetworkResult.Loading) {
            userViewModel.clearState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DefaultBackground)
            .padding(horizontal = 20.sdp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DefaultBackground)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(40.sdp))
            LoadImage(
                image = R.drawable.logo,
                modifier = Modifier
                    .padding(all = 1.sdp)
                    .align(Alignment.CenterHorizontally),
                size = 80.sdp,
                cornerRadius = 40.sdp
            )

            Spacer(modifier = Modifier.height(20.sdp))
            Text(
                text = "Create your account",
                fontSize = 15.ssp,
                fontWeight = FontWeight.Bold,
                color = DefaultColor,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(30.sdp))
            CustomLabel("Full Name")
            Spacer(modifier = Modifier.height(4.sdp))
            CustomTextField(hint = "Full Name", value = fullName, onValueChange = { fullName = it })

            Spacer(modifier = Modifier.height(16.sdp))
            CustomLabel("Email Address")
            Spacer(modifier = Modifier.height(4.sdp))
            CustomTextField(hint = "Email Address", value = email, onValueChange = { email = it })

            Spacer(modifier = Modifier.height(16.sdp))
            CustomLabel("Gender")
            Spacer(modifier = Modifier.height(4.sdp))
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
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

            Spacer(modifier = Modifier.height(16.sdp))
            CustomLabel("Password")
            Spacer(modifier = Modifier.height(4.sdp))
            CustomPasswordField(hint = "Password", value = password, onValueChange = { password = it })

            Spacer(modifier = Modifier.height(16.sdp))
            CustomLabel("Confirm Password")
            Spacer(modifier = Modifier.height(4.sdp))
            CustomPasswordField(hint = "Password", value = confirmPassword, onValueChange = { confirmPassword = it })

            Spacer(modifier = Modifier.height(26.sdp))
            CustomButton("Create Account") {
                if (fullName.isBlank()) {
                    context.showToast("Full name cannot be empty")
                } else if (email.isBlank()) {
                    context.showToast("Email cannot be empty")
                } else if (!isValidEmail(email)) {
                    context.showToast("Email is not valid")
                } else if (password.isBlank()) {
                    context.showToast("Password cannot be empty")
                } else if (confirmPassword.isBlank()) {
                    context.showToast("Confirm password cannot be empty")
                } else if (password != confirmPassword) {
                    context.showToast("Password and confirm password do not match")
                } else {
                    userViewModel.registerUser(UserRequest(fullName, email, gender, password, ""))
                }
            }

            Row(
                modifier = Modifier
                    .padding(top = 26.sdp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Already have an account?",
                    fontSize = 12.ssp,
                    color = BlackTextColor
                )
                Spacer(modifier = Modifier.width(4.sdp))
                Text(
                    text = "Login",
                    fontSize = 12.ssp,
                    fontWeight = FontWeight.Bold,
                    color = DefaultColor,
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.sdp))

        }
    }

}