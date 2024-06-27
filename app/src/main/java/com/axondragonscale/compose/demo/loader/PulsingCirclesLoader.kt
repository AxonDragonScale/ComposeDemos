package com.axondragonscale.compose.demo.loader

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

/**
 * Created by Ronak Harkhani on 23/04/24
 */

@Composable
fun PulsingCirclesLoader(
    modifier: Modifier = Modifier,
    numCircles: Int = 8,
    durationPerCircle: Int = 200,
    circleSize: Dp = 16.dp,
    circleColor: Color = MaterialTheme.colorScheme.primary,
    spaceBy: Dp = 8.dp,
) {
    val transition = rememberInfiniteTransition("")
    val circleScales = arrayListOf<State<Float>>()
    for (i in 0 until numCircles) {
        val delay = durationPerCircle * i
        val scale = transition.animateFloat(
            initialValue = 0f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = durationPerCircle * numCircles

                    0f at 0 using LinearEasing
                    1f at delay using LinearEasing
                    0f at durationMillis using LinearEasing
                },
                repeatMode = RepeatMode.Restart,
            ),
            label = "scale-$i"
        )
        circleScales.add(scale)
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBy)
    ) {
        circleScales.forEach { scale ->
            Box(
                modifier = Modifier
                    .size(circleSize)
                    .scale(scale.value)
                    .background(circleColor, CircleShape)
            )
        }
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
