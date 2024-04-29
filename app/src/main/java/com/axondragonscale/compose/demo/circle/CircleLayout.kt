package com.axondragonscale.compose.demo.circle

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.roundToIntRect
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
import kotlin.math.cos
import kotlin.math.sin

/**
 * Created by Ronak Harkhani on 24/04/24
 */

@Composable
fun CircleLayout(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularLayout {
            repeat(10) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(25)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it.toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }

        CircularLayout(radius = 50.dp) {
            repeat(10) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = MaterialTheme.colorScheme.error,
                            shape = RoundedCornerShape(50)
                        )
                )
            }
        }
    }
}

@Composable
fun CircularLayout(
    modifier: Modifier = Modifier,
    radius: Dp = 100.dp,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        // Measure all the children
        val placeables = measurables.map { it.measure(constraints) }

        // Create a bounding rect of size = radius + largest item size and find its center
        val radiusPx = with(density) { radius.toPx() }
        val placeableSize = with(placeables.first()) { maxOf(width, height) }
        val parentRect = Rect(Offset.Zero, radius = radiusPx + placeableSize).roundToIntRect()
        val center = IntOffset(parentRect.width / 2, parentRect.height / 2)

        val anglePerItem = -Math.toRadians(360.0 / placeables.size)
        layout(parentRect.width, parentRect.height) {
            var currentAngle = 0.0
            placeables.forEach { placeable ->
                placeable.placeRelative(
                    center.x + (radiusPx * sin(currentAngle)).toInt() - placeable.width / 2,
                    center.y + (radiusPx * cos(currentAngle)).toInt() - placeable.height / 2,
                )

                currentAngle += anglePerItem
            }
        }
    }
}


@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CircleLayoutPreview() {
    ComposeDemosTheme {
        Surface {
            CircleLayout()
        }
    }
}
