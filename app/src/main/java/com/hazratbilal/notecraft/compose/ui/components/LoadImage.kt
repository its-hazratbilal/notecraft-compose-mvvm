package com.hazratbilal.notecraft.compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.hazratbilal.notecraft.compose.R
import com.hazratbilal.notecraft.compose.utils.sdp


@Composable
fun LoadImage(
    image: Any,
    modifier: Modifier = Modifier,
    size: Dp,
    cornerRadius: Dp,
    imagePadding: Dp = 0.sdp,
    backgroundColor: Color = Color.Transparent,
    contentScale: ContentScale = ContentScale.Fit
) {
    var isLoading by remember { mutableStateOf(true) }
    val loaderSize = size * 0.25f

    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_error)
                .crossfade(true)
                .build(),
            contentDescription = "Image",
            contentScale = contentScale,
            modifier = Modifier
                .fillMaxSize()
                .padding(all = imagePadding)
                .clip(RoundedCornerShape(cornerRadius)),
            onState = { state ->
                isLoading = state is AsyncImagePainter.State.Loading
            }

        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(loaderSize)
            )
        }
    }
}
