@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.zyc.lib.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

/**
 * @author devzyc
 */
@OptIn(ExperimentalCoilApi::class)
@Composable
fun RemoteImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    loadingContent: @Composable (() -> Unit)? = null,
) {
    val painter =
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = imageUrl)
                .crossfade(true)
                .build()
        )

    val state = painter.state
    if (state is AsyncImagePainter.State.Loading) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            if (loadingContent == null) {
                CircularProgressIndicator()
            } else {
                loadingContent.invoke()
            }
        }
    }

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.FillBounds
    )
}
