package com.zyc.lib.widget.dotloading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.*

/**
 * @author devzyc
 */
const val NumDots = 5 //dots count
const val AnimationDuration = 2000
const val AnimationSegment = AnimationDuration / 10

@Composable
fun DotLoading(
    dotSize: Dp = 12.dp,
    modifier: Modifier = Modifier.padding(32.dp),
    color: Color = MaterialTheme.colors.primary,
) {
    val state = rememberDotState()
    LaunchedEffect(true) {
        state.start(this)
    }

    Layout(
        content = {
            val minFactor = .3f
            val step = minFactor / NumDots
            repeat(NumDots) { index ->
                val size = dotSize * (1f - step * index)
                Dot(
                    color = color,
                    modifier = Modifier
                        .requiredSize(size)
                        .graphicsLayer {
                            alpha = state[index].alphaFromRadians
                        }
                )
            }
        },
        modifier = modifier
    ) { measurables, constraints ->
        val looseConstraints = constraints.copy(
            minWidth = 0,
            maxHeight = 0
        )
        val placeables = measurables.map { measurable ->
            measurable.measure(looseConstraints)
        }

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            val radius = min(constraints.maxWidth, constraints.maxHeight) / 2f
            placeables.forEachIndexed { index, placeable ->
                val animatedValue = state[index]
                val x = (radius + radius * sin(animatedValue)).roundToInt()
                val y = (radius - radius * cos(animatedValue)).roundToInt()
                placeable.placeRelative(
                    x = x,
                    y = y
                )
            }
        }
    }
}

private val Float.alphaFromRadians: Float
    get() {
        val normalized = (this / 2f * PI).toFloat()
        return .5f + (normalized - .5f).absoluteValue
    }

@Composable
fun Dot(
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
fun rememberDotState(): DotState = remember {
    DotStateImpl()
}