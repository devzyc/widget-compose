package com.zyc.lib.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.max

val AppBarHorizontalPadding = 4.dp

val TitleIconModifier = Modifier.fillMaxHeight()

var iconWidth = 172.dp - AppBarHorizontalPadding // 加了100，可能有问题

var withoutIconWidth = 16.dp - AppBarHorizontalPadding

@Composable
fun CenterTopAppBar(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit) = {},
    backgroundColor: Color = colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
    appBarHeight: Dp,
    content: @Composable () -> Unit,
) {
    val defLeftSectionWidth = if (navigationIcon == null) withoutIconWidth else iconWidth
    var leftSectionWidth by remember { mutableStateOf(defLeftSectionWidth) }

    var rightSectionWidth by remember { mutableStateOf(-1f) }
    var rightSectionPadding by remember { mutableStateOf(0f) }

    AppBar(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        contentPadding = AppBarDefaults.ContentPadding,
        shape = RectangleShape,
        modifier = modifier,
        appBarHeight = appBarHeight,
    ) {
        if (navigationIcon == null) {
            Spacer(Modifier.width(leftSectionWidth))
        } else {
            Row(
                modifier = TitleIconModifier.width(leftSectionWidth),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompositionLocalProvider(
                    values = arrayOf(LocalContentAlpha provides ContentAlpha.high),
                    content = navigationIcon
                )
            }
        }

        Row(
            Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (leftSectionWidth != defLeftSectionWidth
                || rightSectionPadding != 0f
            ) {
                ProvideTextStyle(value = MaterialTheme.typography.h6) {
                    CompositionLocalProvider(
                        values = arrayOf(LocalContentAlpha provides ContentAlpha.high),
                        content = content
                    )
                }
            }
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            with(LocalDensity.current) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = rightSectionPadding.toDp())
                        .onGloballyPositioned {
                            rightSectionWidth = it.size.width.toFloat()
                            if (leftSectionWidth == defLeftSectionWidth
                                && rightSectionWidth != -1f
                                && rightSectionPadding == 0f
                            ) {
                                // Find the maximum width of the sections (left or right).
                                // As a result, both sections should have the same width.
                                val maxWidth = max(
                                    a = leftSectionWidth.value
                                        .toDp()
                                        .toPx(),
                                    b = rightSectionWidth
                                )
                                leftSectionWidth = maxWidth.toDp()
                                rightSectionPadding = abs(rightSectionWidth - maxWidth)
                            }
                        },
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = actions
                )
            }
        }
    }
}

@Composable
fun AppBar(
    backgroundColor: Color,
    contentColor: Color,
    elevation: Dp,
    contentPadding: PaddingValues,
    shape: Shape,
    modifier: Modifier = Modifier,
    appBarHeight: Dp,
    content: @Composable (RowScope.() -> Unit),
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        shape = shape,
        modifier = modifier
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(contentPadding)
                .height(appBarHeight),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}
