package com.hazratbilal.notecraft.compose.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.hazratbilal.notecraft.compose.theme.BlackTextColor
import com.hazratbilal.notecraft.compose.theme.ButtonColor
import com.hazratbilal.notecraft.compose.theme.ButtonStrokeColor
import com.hazratbilal.notecraft.compose.theme.DefaultColor
import com.hazratbilal.notecraft.compose.theme.DefaultTextColor
import com.hazratbilal.notecraft.compose.theme.DisabledBackground
import com.hazratbilal.notecraft.compose.theme.EditTextBorder
import com.hazratbilal.notecraft.compose.theme.HintTextColor
import com.hazratbilal.notecraft.compose.theme.WhiteColor
import com.hazratbilal.notecraft.compose.utils.sdp
import com.hazratbilal.notecraft.compose.utils.ssp


@Composable
fun CustomLabel(label: String) {
    Text(
        text = label,
        fontWeight = FontWeight.Bold,
        fontSize = 13.ssp,
        color = DefaultTextColor
    )
}

@Composable
fun CustomTextField(
    hint: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = hint,
                style = TextStyle(
                    fontSize = 12.ssp,
                    color = HintTextColor
                )
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(42.sdp)
            .border(
                width = 1.sdp,
                color = EditTextBorder,
                shape = RoundedCornerShape(4.sdp)
            )
            .background(WhiteColor),
        textStyle = TextStyle(
            fontSize = 12.ssp,
            color = BlackTextColor
        ),
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
            unfocusedContainerColor = Color.Transparent,
        )
    )
}

@Composable
fun CustomDisabledTextField(
    value: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.sdp)
            .background(
                brush = SolidColor(DisabledBackground),
                shape = RoundedCornerShape(4.sdp)
            )
            .border(
                width = 1.sdp,
                color = EditTextBorder,
                shape = RoundedCornerShape(4.sdp)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = value,
            fontSize = 12.ssp,
            color = BlackTextColor,
            modifier = Modifier
                .padding(horizontal = 13.sdp)
        )
    }
}

@Composable
fun CustomPasswordField(
    hint: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = hint,
                style = TextStyle(
                    fontSize = 12.ssp,
                    color = HintTextColor
                )
            )
        },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .height(42.sdp)
            .border(
                width = 1.sdp,
                color = EditTextBorder,
                shape = RoundedCornerShape(4.sdp)
            )
            .background(WhiteColor),
        textStyle = TextStyle(
            fontSize = 12.ssp,
            color = BlackTextColor
        ),
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

@Composable
fun CustomMultilineTextField(
    hint: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = {
            if (it.length <= 450) {
                onValueChange(it)
            }
        },
        placeholder = {
            Text(
                text = hint,
                style = TextStyle(
                    fontSize = 12.ssp,
                    color = HintTextColor
                )
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.sdp)
            .border(
                width = 1.sdp,
                color = EditTextBorder,
                shape = RoundedCornerShape(4.sdp)
            )
            .background(WhiteColor),
        textStyle = TextStyle(
            fontSize = 12.ssp,
            color = BlackTextColor
        ),
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

@Composable
fun CustomButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier
            .fillMaxWidth()
            .height(42.sdp)
            .then(modifier),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonColor,
            contentColor = WhiteColor
        ),
        shape = RoundedCornerShape(4.sdp),
        border = BorderStroke(
            width = 1.sdp,
            color = ButtonStrokeColor
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            fontSize = 13.ssp,
            color = WhiteColor
        )
    }
}

