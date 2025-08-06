package com.hazratbilal.notecraft.compose.ui.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.hazratbilal.notecraft.compose.remote.NetworkResult
import androidx.compose.runtime.getValue
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hazratbilal.notecraft.compose.model.UserRequest
import com.hazratbilal.notecraft.compose.theme.BlackTextColor
import com.hazratbilal.notecraft.compose.theme.DarkGrayTextColor
import com.hazratbilal.notecraft.compose.theme.DefaultColor
import com.hazratbilal.notecraft.compose.theme.EditTextBorder
import com.hazratbilal.notecraft.compose.theme.HintTextColor
import com.hazratbilal.notecraft.compose.theme.WhiteColor
import com.hazratbilal.notecraft.compose.ui.components.CustomButton
import com.hazratbilal.notecraft.compose.ui.components.CustomLabel
import com.hazratbilal.notecraft.compose.ui.components.Loading
import com.hazratbilal.notecraft.compose.utils.Constant
import com.hazratbilal.notecraft.compose.utils.Helper.Companion.isValidEmail
import com.hazratbilal.notecraft.compose.utils.SharedPrefs
import com.hazratbilal.notecraft.compose.utils.sdp
import com.hazratbilal.notecraft.compose.utils.showToast
import com.hazratbilal.notecraft.compose.utils.ssp
import com.hazratbilal.notecraft.compose.R
import com.hazratbilal.notecraft.compose.ui.components.LoadImage


@Composable
fun LoginScreen(navController: NavHostController, sharedPrefs: SharedPrefs) {

    val context = LocalContext.current

    val userViewModel: UserViewModel = hiltViewModel()
    val userResponse by userViewModel.userResponseStateFlow.collectAsState()

    var email = rememberSaveable { mutableStateOf("") }
    var password = rememberSaveable { mutableStateOf("") }

    Loading(isLoading = userResponse is NetworkResult.Loading)

    LaunchedEffect(userResponse) {
        when (userResponse) {
            is NetworkResult.Success -> {
                val userData = userResponse.data
                if (userData?.success == true) {

                    sharedPrefs.putString(Constant.FULL_NAME, userData.user.full_name)
                    sharedPrefs.putString(Constant.EMAIL, userData.user.email)
                    sharedPrefs.putString(Constant.GENDER, userData.user.gender)
                    sharedPrefs.putString(Constant.TOKEN, userData.user.token)

					context.showToast("Login successful")

                    navController.navigate("notes") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }

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


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 20.sdp
            ),
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
            text = "Login into your account",
            fontSize = 15.ssp,
            fontWeight = FontWeight.Bold,
            color = DefaultColor,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(30.sdp))
        CustomLabel("Email Address")
        Spacer(modifier = Modifier.height(4.sdp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.sdp)
                .background(
                    color = WhiteColor,
                    shape = RoundedCornerShape(4.sdp)
                )
                .border(
                    width = 1.sdp,
                    color = EditTextBorder,
                    shape = RoundedCornerShape(4.sdp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_email),
                contentDescription = "Email Address Icon",
                modifier = Modifier
                    .padding(start = 14.sdp)
                    .size(18.sdp),
                colorFilter = ColorFilter.tint(DarkGrayTextColor)
            )

            Box(
                modifier = Modifier
                    .padding(
                        start = 12.sdp,
                        bottom = 8.sdp,
                        top = 8.sdp
                    )
                    .width(1.sdp)
                    .fillMaxHeight()
                    .background(EditTextBorder)
            )

            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                placeholder = {
                    Text(
                        text = "Email Address",
                        style = TextStyle(
                            fontSize = 12.ssp,
                            color = HintTextColor
                        ),
                    )
                },
                textStyle = TextStyle(
                    color = BlackTextColor,
                    fontSize = 12.ssp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    cursorColor = DefaultColor,
                    selectionColors = TextSelectionColors(
                        handleColor = DefaultColor,
                        backgroundColor = DefaultColor.copy(alpha = 0.4f)
                    ),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(16.sdp))
        CustomLabel("Password")
        Spacer(modifier = Modifier.height(4.sdp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.sdp)
                .background(
                    color = WhiteColor,
                    shape = RoundedCornerShape(4.sdp)
                )
                .border(
                    width = 1.sdp,
                    color = EditTextBorder,
                    shape = RoundedCornerShape(4.sdp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_password),
                contentDescription = "Password Icon",
                modifier = Modifier
                    .padding(start = 12.sdp)
                    .size(18.sdp),
                colorFilter = ColorFilter.tint(DarkGrayTextColor)
            )

            Box(
                modifier = Modifier
                    .padding(
                        start = 12.sdp,
                        top = 8.sdp,
                        bottom = 8.sdp
                    )
                    .width(1.sdp)
                    .fillMaxHeight()
                    .background(EditTextBorder)
            )

            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                placeholder = {
                    Text(
                        text = "Password",
                        style = TextStyle(
                            fontSize = 12.ssp,
                            color = HintTextColor
                        )
                    )
                },
                textStyle = TextStyle(
                    color = BlackTextColor,
                    fontSize = 12.ssp
                ),
				visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    cursorColor = DefaultColor,
                    selectionColors = TextSelectionColors(
                        handleColor = DefaultColor,
                        backgroundColor = DefaultColor.copy(alpha = 0.4f)
                    ),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),

            )
        }

        Spacer(modifier = Modifier.height(26.sdp))

        CustomButton("Login") {
            if (email.value.isBlank() || password.value.isBlank()) {
                context.showToast("Fields can't be empty")
            } else if (!isValidEmail(email.value)) {
                context.showToast("Invalid email")
            } else {
                userViewModel.loginUser(UserRequest("", email.value, "", password.value, ""))
            }
        }

        Spacer(modifier = Modifier.height(26.sdp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Don't have an account?",
                style = TextStyle(
                    fontSize = 12.ssp,
                    color = BlackTextColor
                )
            )
            Spacer(modifier = Modifier.width(4.sdp))
            Text(
                text = "Create Account",
                style = TextStyle(
                    fontSize = 12.ssp,
                    color = BlackTextColor
                ),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate("registration")
                }
            )
        }

        Spacer(modifier = Modifier.height(24.sdp))
    }
}
