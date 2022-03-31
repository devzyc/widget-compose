@file:Suppress("TrailingComma")

package com.zyc.lib.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun GotoTop(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    scope: CoroutineScope,
    showBottomBar: () -> Unit,
    fabBottomPadding: Dp,
) {
    val showScrollButton = derivedStateOf {
        listState.firstVisibleItemIndex > 0
    }

    AnimatedVisibility(
        visible = showScrollButton.value,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = modifier
                .padding(vertical = 16.dp)
                .statusBarsPadding(),
            contentAlignment = Alignment.BottomEnd,
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        end = 18.dp,
                        bottom = fabBottomPadding,
                    ),
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                    showBottomBar.invoke()
                },
                containerColor = colors.primary,
                shape = CircleShape,
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowUpward,
                    contentDescription = "Scroll Up button",
                    tint = colors.background,
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                )
            }
        }
    }
}
