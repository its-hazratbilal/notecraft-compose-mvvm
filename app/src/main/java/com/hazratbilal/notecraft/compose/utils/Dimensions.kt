package com.hazratbilal.notecraft.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


val Int.sdp: Dp
    @Composable
    get() = this.sdpGet()

val Int.ssp: TextUnit
    @Composable
    get() = this.sspGet()

@Composable
private fun Int.sdpGet(): Dp {
    val id = when (this) {
        in 1..600 -> "_${this}sdp"
        in -60..-1 -> "_minus${-this}sdp"
        else -> return this.dp
    }
    val resourceField = getFieldId(id)
    return if (resourceField != 0) dimensionResource(id = resourceField) else this.dp
}

@Composable
private fun Int.sspGet(): TextUnit {
    val id = when (this) {
        in 1..600 -> "_${this}ssp"
        in -60..-1 -> "_minus${-this}ssp"
        else -> return this.sp
    }
    val resourceField = getFieldId(id)
    return if (resourceField != 0) {
        with(LocalDensity.current) {
            dimensionResource(id = resourceField).toSp()
        }
    } else {
        this.sp
    }
}

@Composable
private fun getFieldId(id: String): Int {
    val context = LocalContext.current
    return context.resources.getIdentifier(id, "dimen", context.packageName)
}
