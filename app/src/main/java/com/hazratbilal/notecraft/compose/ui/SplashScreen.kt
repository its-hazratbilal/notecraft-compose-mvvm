package com.hazratbilal.notecraft.compose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.commandiron.compose_loading.ThreeBounce
import com.hazratbilal.notecraft.compose.theme.DefaultColor
import com.hazratbilal.notecraft.compose.utils.SharedPrefs
import com.hazratbilal.notecraft.compose.utils.sdp
import com.hazratbilal.notecraft.compose.utils.ssp
import kotlinx.coroutines.delay
import com.hazratbilal.notecraft.compose.R
import com.hazratbilal.notecraft.compose.utils.Constant


@Composable
fun SplashScreen(navController: NavHostController, sharedPrefs: SharedPrefs) {

    LaunchedEffect(Unit) {
        delay(2000)
        if (sharedPrefs.getString(Constant.TOKEN).isEmpty()) {
            navController.navigate("login") {
                popUpTo("splash") {
                    inclusive = true
                }
            }
        } else {
            navController.navigate("notes") {
                popUpTo("splash") {
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(100.sdp)
            )

            Spacer(modifier = Modifier.height(12.sdp))

            Text(
                text = stringResource(id = R.string.app_name),
                color = DefaultColor,
                fontWeight = FontWeight.Bold,
                fontSize = 15.ssp
            )

            ThreeBounce(color = DefaultColor)

        }
    }
}
