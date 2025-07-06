package com.axondragonscale.compose.demo.loader.keyframe

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Created by Ronak Harkhani on 23/04/24
 */

@Composable
fun BouncingCirclesLoader(
    modifier: Modifier = Modifier,
    duration: Int = 1000,
    circleSize: Dp = 16.dp,
    circleColor: Color = MaterialTheme.colorScheme.primary,
    spaceBy: Dp = 8.dp,
    bounceHeight: Dp = 16.dp,
) {
    val circleHeights = listOf(
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
    )

    circleHeights.forEachIndexed { index, height ->
        LaunchedEffect(height) {
            height.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = duration

                        0f at (duration * 0) using LinearOutSlowInEasing
                        1f at (duration / 4) using LinearOutSlowInEasing
                        0f at (duration / 2) using LinearOutSlowInEasing
                        0f at duration using LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart,
                    initialStartOffset = StartOffset(index * 100)
                )
            )
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBy)
    ) {
        circleHeights.forEach { height ->
            Box(
                modifier = Modifier
                    .offset(y = -bounceHeight * height.value) // Apply offset before background
                    .size(circleSize)
                    .background(circleColor, CircleShape)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MaterialTheme {
        Surface {
            Loaders()
        }
    }
}
