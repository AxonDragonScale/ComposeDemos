package com.axondragonscale.compose.demo.loader.keyframe

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

/**
 * Created by Ronak Harkhani on 23/04/24
 */

@Composable
fun NewtonCradleLoader(
    modifier: Modifier = Modifier,
    maxOffset: Dp = 16.dp,
    duration: Int = 400,
    numCircles: Int = 5,
    circleSize: Dp = 16.dp,
    circleColor: Color = MaterialTheme.colorScheme.primary,
    spaceBy: Dp = 1.dp,
) {
    // Divide animation in 5 parts
    // 1 and 2 -> Left circle moves from left to center
    // 3 -> Nothing
    // 4 and 5 -> Right circle moves from center to right
    val transition = rememberInfiniteTransition()
    val offsetLeft = transition.animateFloat(
        initialValue = -1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = duration

                (-1f) at 0
                0f at (duration * 2)/5 using LinearEasing
                0f at duration
            },
            repeatMode = RepeatMode.Reverse
        ),
        label = "leftCircle"
    )
    val offsetRight = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = duration

                0f at 0
                0f at (duration * 3)/5
                1f at duration using LinearEasing
            },
            repeatMode = RepeatMode.Reverse
        ),
        label = "rightCircle"
    )

    @Composable
    fun Circle(modifier: Modifier = Modifier) {
        Box(modifier = modifier.size(circleSize).background(circleColor, CircleShape))
    }

    Row(
        modifier = modifier.padding(horizontal = maxOffset),
        horizontalArrangement = Arrangement.spacedBy(spaceBy)
    ) {
        Circle(modifier = Modifier.offset(x = maxOffset * offsetLeft.value))
        repeat(numCircles - 2) { Circle() }
        Circle(modifier = Modifier.offset(x = maxOffset * offsetRight.value))
    }
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ComposeDemosTheme {
        Surface {
            Loaders()
        }
    }
}
