package com.hazratbilal.notecraft.compose.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hazratbilal.notecraft.compose.R
import com.hazratbilal.notecraft.compose.theme.BlackTextColor
import com.hazratbilal.notecraft.compose.theme.DefaultBackground
import com.hazratbilal.notecraft.compose.theme.DefaultColor
import com.hazratbilal.notecraft.compose.utils.sdp
import com.hazratbilal.notecraft.compose.utils.ssp


@Composable
fun ExitDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Dialog(
        onDismissRequest = { onCancel() },
        properties = DialogProperties(
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp)
                .background(Color.Transparent)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.sdp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.sdp),
                    shape = RoundedCornerShape(8.sdp),
                    border = BorderStroke(1.dp, DefaultColor),
                    colors = CardDefaults.cardColors(containerColor = DefaultBackground)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.sdp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Are you sure you want to exit?",
                            fontSize = 13.ssp,
                            color = BlackTextColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 50.sdp)
                                .fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.sdp, bottom = 12.sdp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CustomButton(text = "Yes",  modifier = Modifier.weight(1f).height(34.sdp)) {
                                onConfirm()
                            }
                            Spacer(modifier = Modifier.width(12.sdp))
                            CustomButton(text = "Cancel",  modifier = Modifier.weight(1f).height(34.sdp)) {
                                onCancel()
                            }
                        }
                    }
                }
            }

            LoadImage(
                image = R.drawable.logo,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .background(Color.Transparent)
                    .border(
                        width = 1.dp,
                        color = DefaultColor,
                        shape = RoundedCornerShape(30.sdp)
                    ),
                size = 60.sdp,
                cornerRadius = 30.sdp,
                imagePadding = 2.sdp,
                backgroundColor = DefaultBackground
            )

        }
    }
}
