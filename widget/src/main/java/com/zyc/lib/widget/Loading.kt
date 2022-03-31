package com.zyc.lib.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * @author devzyc
 */
@Composable
fun Loading(
    isShown: Boolean,
    content: @Composable (() -> Unit)? = null,
) {
    if (isShown) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (content == null) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            } else {
                content.invoke()
            }
        }
    }
}