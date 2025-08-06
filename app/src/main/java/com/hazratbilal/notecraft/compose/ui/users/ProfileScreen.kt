package com.hazratbilal.notecraft.compose.ui.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import com.hazratbilal.notecraft.compose.theme.DefaultBackground
import com.hazratbilal.notecraft.compose.ui.components.CustomButton
import com.hazratbilal.notecraft.compose.ui.components.CustomDisabledTextField
import com.hazratbilal.notecraft.compose.ui.components.CustomLabel
import com.hazratbilal.notecraft.compose.utils.Constant
import com.hazratbilal.notecraft.compose.utils.SharedPrefs
import com.hazratbilal.notecraft.compose.utils.sdp


@Composable
fun ProfileScreen(navController: NavHostController, sharedPrefs: SharedPrefs) {

    var fullName by rememberSaveable { mutableStateOf(sharedPrefs.getString(Constant.FULL_NAME)) }
    var gender by rememberSaveable { mutableStateOf(sharedPrefs.getString(Constant.GENDER)) }

    val savedStateHandle = remember(navController) {
        navController.currentBackStackEntry?.savedStateHandle
    }
    val profileUpdated = savedStateHandle?.get<Boolean>("profileUpdated") ?: false

    LaunchedEffect(Unit) {
        if (profileUpdated) {
            fullName = sharedPrefs.getString(Constant.FULL_NAME)
            gender = sharedPrefs.getString(Constant.GENDER)

            savedStateHandle?.remove<Boolean>("profileUpdated")
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
        CustomDisabledTextField(value = fullName)


        Spacer(modifier = Modifier.height(16.sdp))
        CustomLabel("Gender")
        Spacer(modifier = Modifier.height(4.sdp))
        CustomDisabledTextField(value = gender)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 26.sdp),
            horizontalArrangement = Arrangement.spacedBy(12.sdp)
        ) {
            CustomButton("Change Password", Modifier.weight(1f)) {
                navController.navigate("change_password")
            }

            CustomButton("Edit Profile", Modifier.weight(1f)) {
                navController.navigate("edit_profile")
            }
        }

        Spacer(modifier = Modifier.height(24.sdp))
    }
}
