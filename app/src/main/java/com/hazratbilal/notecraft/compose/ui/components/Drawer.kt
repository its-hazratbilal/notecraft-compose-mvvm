package com.hazratbilal.notecraft.compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.hazratbilal.notecraft.compose.R
import com.hazratbilal.notecraft.compose.theme.BlackTextColor
import com.hazratbilal.notecraft.compose.theme.DefaultBackground
import com.hazratbilal.notecraft.compose.theme.DividerColor
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hazratbilal.notecraft.compose.theme.DefaultTextColor
import com.hazratbilal.notecraft.compose.theme.DrawerItemOnclick
import com.hazratbilal.notecraft.compose.theme.DrawerTextOnclick
import com.hazratbilal.notecraft.compose.utils.sdp
import com.hazratbilal.notecraft.compose.utils.ssp


@Composable
fun Drawer(
    userName: String,
    userEmail: String,
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(233.sdp)
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .verticalScroll(rememberScrollState())
            .background(DefaultBackground)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.sdp),
        ) {
            Spacer(modifier = Modifier.height(40.sdp))
            LoadImage(
                image = R.drawable.logo,
                modifier = Modifier
                    .padding(all = 1.sdp),
                size = 80.sdp,
                cornerRadius = 40.sdp
            )

            Spacer(modifier = Modifier.height(12.sdp))
            Text(
                text = userName,
                fontWeight = FontWeight.Bold,
                fontSize = 13.ssp,
                color = BlackTextColor
            )
            Text(
                text = userEmail,
                fontWeight = FontWeight.Bold,
                fontSize = 11.ssp,
                color = BlackTextColor
            )

            Spacer(modifier = Modifier.height(12.sdp))
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = DividerColor
        )

        Spacer(modifier = Modifier.height(12.sdp))
        DrawerItem(
            iconRes = R.drawable.ic_profile,
            label = "Profile",
            onClick = onProfileClick
        )

        DrawerItem(
            iconRes = R.drawable.ic_logout,
            label = "Logout",
            onClick = onLogoutClick
        )

    }
}

@Composable
fun DrawerItem(
    iconRes: Int,
    label: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val itemColor = if (isPressed) DrawerTextOnclick else DefaultTextColor
    val backgroundColor = if (isPressed) DrawerItemOnclick else Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.sdp)
            .background(backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
            .padding(horizontal = 20.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = itemColor,
            modifier = Modifier.size(20.sdp)
        )
        Spacer(modifier = Modifier.width(12.sdp))
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            color = itemColor
        )
    }
}

