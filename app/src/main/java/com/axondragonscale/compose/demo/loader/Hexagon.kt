package com.axondragonscale.compose.demo.loader

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
import kotlinx.coroutines.launch

/**
 * Created by Ronak Harkhani on 20/10/24
 */

@Composable
fun HexagonLoader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        var isLoading by remember { mutableStateOf(true) }
        HexagonLoader(
            modifier = Modifier.fillMaxSize(0.6f),
            isLoading = isLoading,
            onClick = { isLoading = !isLoading },
        )
    }
}

@Composable
private fun HexagonLoader(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
) {
    var rotation by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(isLoading) {
        if (!isLoading) return@LaunchedEffect
        animate(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearEasing),
            )
        ) { value, velocity ->
            rotation = value
        }
    }

    var clickOffset by remember { mutableStateOf(Offset.Zero) }
    var clickRadius by remember { mutableFloatStateOf(0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier.aspectRatio(1 / 1.1547005f),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { offset ->
                        onClick()
                        clickOffset = offset
                        scope.launch {
                            animate(
                                initialValue = 0f,
                                targetValue = 1f,
                                animationSpec = tween(durationMillis = 400),
                            ) { value, velocity ->
                                clickRadius = value
                            }
                            clickRadius = 0f
                        }
                    })
                },
        ) {
            val hexagonPath = createHexagonPath(size)

            if (isLoading) {
                clipPath(hexagonPath) {
                    rotate(degrees = rotation) {
                        scale(scale = 1.2f) {
                            drawArc(
                                startAngle = 0f,
                                sweepAngle = 180f,
                                useCenter = true,
                                brush = Brush.sweepGradient(
                                    listOf(
                                        backgroundColor,
                                        backgroundColor,
                                        backgroundColor,
                                        color.copy(0.5f),
                                        color.copy(0.5f),
                                        color.copy(0.5f),
                                        color,
                                        color,
                                        color,
                                    )
                                ),
                            )
                        }
                    }
                }
            }

            drawPath(
                path = hexagonPath,
                color = color,
                style = Stroke(width = 1.dp.toPx(), cap = StrokeCap.Round)
            )

            scale(scale = 0.6f) {
                drawPath(
                    path = hexagonPath,
                    color = color,
                    style = Fill,
                )
            }

            clipPath(hexagonPath) {
                drawCircle(
                    color = color.copy(alpha = 0.2f),
                    radius = clickRadius * size.maxDimension * 2,
                    center = clickOffset,
                )
            }
        }
    }
}

private fun createHexagonPath(size: Size) = Path().apply {
    moveTo(size.width / 2f, 0f) // Start at top center
    lineTo(size.width, size.height / 4f)
    lineTo(size.width, size.height * 3 / 4f)
    lineTo(size.width / 2f, size.height)
    lineTo(0f, size.height * 3 / 4f)
    lineTo(0f, size.height / 4f)
    close()
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ComposeDemosTheme {
        Surface {
            HexagonLoader()
        }
    }
}
