package com.axondragonscale.compose.demo.loader.keyframe

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.circle.CircularLayout
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

/**
 * Created by Ronak Harkhani on 24/04/24
 */


@Composable
fun RingedCirclesLoader(
    modifier: Modifier = Modifier,
    minAlpha: Float = 0.2f,
    numCircles: Int = 8,
    durationPerCircle: Int = 200,
    circleSize: Dp = 16.dp,
    circleColor: Color = MaterialTheme.colorScheme.primary,
) {
    val transition = rememberInfiniteTransition("")
    val circleAlphas = arrayListOf<State<Float>>()
    for (i in 0 until numCircles) {
        val delay = durationPerCircle * i
        val alpha = transition.animateFloat(
            initialValue = minAlpha,
            targetValue = minAlpha,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = durationPerCircle * numCircles
                    1f at 0 using LinearEasing
                    minAlpha at durationMillis using LinearEasing
                },
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(delay)
            ),
            label = "alpha-$i"
        )
        circleAlphas.add(alpha)
    }

    CircularLayout(modifier = modifier, radius = 24.dp) {
        circleAlphas.forEach { alpha ->
            Box(
                modifier = Modifier
                    .size(circleSize)
                    .alpha(alpha.value)
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
