package com.hazratbilal.notecraft.compose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hazratbilal.notecraft.compose.R
import com.hazratbilal.notecraft.compose.theme.AppBarBorder
import com.hazratbilal.notecraft.compose.theme.BlackTextColor
import com.hazratbilal.notecraft.compose.theme.DefaultBackground
import com.hazratbilal.notecraft.compose.utils.ssp


@Composable
fun CustomTopAppBar(
    currentRoute: String?,
    onMenuClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val route = currentRoute?.substringBefore("/")
    when (route) {
        "splash", "login", "registration" -> {}

        "notes" -> {
            TopAppBarItem("Notes", true) {
                onMenuClick()
            }
        }

        "create_note" -> {
            TopAppBarItem("Create Note") {
                onBackClick()
            }
        }

        "edit_note" -> {
            TopAppBarItem("Edit Note") {
                onBackClick()
            }
        }

        "profile" -> {
            TopAppBarItem("Profile") {
                onBackClick()
            }
        }

        "edit_profile" -> {
            TopAppBarItem("Edit Profile") {
                onBackClick()
            }
        }

        "change_password" -> {
            TopAppBarItem("Change Password") {
                onBackClick()
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarItem(
    title: String,
    isMenu: Boolean = false,
    onClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 17.ssp,
                fontWeight = FontWeight.Medium,
                color = BlackTextColor,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                onClick()
            }) {
                Icon(
                    painter = painterResource(id = if (isMenu) R.drawable.ic_menu else R.drawable.ic_back),
                    tint = BlackTextColor,
                    contentDescription = if (isMenu) "Menu" else "Back"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DefaultBackground,
            scrolledContainerColor = DefaultBackground
        ),
        modifier = Modifier
            .shadow(
                elevation = 1.dp,
                spotColor = AppBarBorder
            )

    )
}
