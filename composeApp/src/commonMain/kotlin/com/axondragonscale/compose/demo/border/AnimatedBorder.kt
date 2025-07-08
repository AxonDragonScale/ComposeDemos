package com.axondragonscale.compose.demo.border

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Created by Ronak Harkhani on 25/04/24
 */

@Composable
fun AnimatedBorder(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedBorderCard(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(100.dp),
            brush = Brush.sweepGradient(
                listOf(
                    Color.Yellow,
                    Color.Blue,
                    Color.Magenta,
                    Color.Green
                )
            ),
            borderWidth = 4.dp,
            duration = 2000,
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "AnimatedBorderCard")
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .aspectRatio(1f)
                .animateBorder(
                    brush = Brush.sweepGradient(
                        listOf(
                            Color.Red,
                            Color.Blue,
                            Color.Green,
                            Color.Cyan,
                            Color.Yellow
                        )
                    ),
                    shape = CutCornerShape(16.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "animateBorder Modifier")
        }
    }
}

@Composable
fun AnimatedBorderCard(
    modifier: Modifier = Modifier,
    brush: Brush = Brush.sweepGradient(listOf(Color.Gray, Color.White)),
    borderWidth: Dp = 2.dp,
    shape: Shape = RoundedCornerShape(10),
    duration: Int = 5000,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit,
) {
    val transition = rememberInfiniteTransition()
    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "border-angle"
    )

    Surface(
        modifier = modifier
            .clip(shape)
            .padding(borderWidth)
            .drawBehind {
                rotate(degrees = angle) {
                    drawCircle(
                        brush = brush,
                        radius = maxOf(size.width, size.height),
                        blendMode = BlendMode.SrcIn
                    )
                }
            },
        color = backgroundColor,
        shape = shape
    ) {
        content()
    }
}

@Composable
fun Modifier.animateBorder(
    brush: Brush = Brush.sweepGradient(listOf(Color.Gray, Color.White)),
    borderWidth: Dp = 2.dp,
    shape: Shape = RoundedCornerShape(10),
    duration: Int = 5000,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
) = composed {
    val transition = rememberInfiniteTransition()
    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "border-angle"
    )

    this
        .clip(shape)
        .padding(borderWidth)
        .drawBehind {
            rotate(degrees = angle) {
                drawCircle(
                    brush = brush,
                    radius = maxOf(size.width, size.height),
                    blendMode = BlendMode.SrcIn
                )
            }
        }
        .background(backgroundColor, shape)
}


@Preview
@Composable
private fun Preview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AnimatedBorder()
        }
    }
}
