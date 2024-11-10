package com.axondragonscale.compose.demo.loader

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
import com.axondragonscale.compose.demo.util.minus
import com.axondragonscale.compose.demo.util.toOffset

/**
 * Created by Ronak Harkhani on 10/11/24
 */

@Composable
fun RingLoaderScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RingLoader(modifier = Modifier.size(120.dp))

        Spacer(modifier = Modifier.height(100.dp))

        RingLoader(
            modifier = Modifier.size(140.dp),
            middle = RingDefaults.middle.copy(rotateClockwise = false)
        )
    }
}

data class RingConfig(
    val color: Color,
    val sizePercent: Float,
    val rotationPeriod: Int = 1000,
    val rotateClockwise: Boolean = true,
    val sweepAngle: Float = 180f,
)

@Composable
private fun RingLoader(
    modifier: Modifier = Modifier,
    outer: RingConfig = RingDefaults.outer,
    middle: RingConfig = RingDefaults.middle,
    inner: RingConfig = RingDefaults.inner,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val outerRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f * if (outer.rotateClockwise) 1f else -1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = outer.rotationPeriod, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        )
    )

    val middleRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f * if (middle.rotateClockwise) 1f else -1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = middle.rotationPeriod, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        )
    )

    val innerRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f * if (inner.rotateClockwise) 1f else -1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = inner.rotationPeriod, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        )
    )

    Canvas(modifier = modifier.aspectRatio(1f)) {
        listOf(outer, middle, inner)
            .zip(listOf(outerRotation, middleRotation, innerRotation))
            .forEach { (ring, rotation) ->
                val ringSize = size * ring.sizePercent
                drawArc(
                    topLeft = (size - ringSize).toOffset() / 2f,
                    color = ring.color,
                    startAngle = rotation,
                    sweepAngle = ring.sweepAngle,
                    useCenter = false,
                    size = size * ring.sizePercent,
                    style = Stroke(
                        width = 8.dp.toPx(),
                        cap = StrokeCap.Round,
                    ),
                )
            }
    }
}

private object RingDefaults {
    val outer = RingConfig(
        color = Color.Blue,
        sizePercent = 1f,
        rotationPeriod = 1000,
    )

    val middle = RingConfig(
        color = Color.Green,
        sizePercent = 0.75f,
        rotationPeriod = 700
    )

    val inner = RingConfig(
        color = Color.Red,
        sizePercent = 0.5f,
        rotationPeriod = 900
    )
}


@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ComposeDemosTheme {
        Surface {
            RingLoaderScreen()
        }
    }
}
