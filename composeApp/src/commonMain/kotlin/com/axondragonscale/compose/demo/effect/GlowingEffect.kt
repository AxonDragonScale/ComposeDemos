package com.axondragonscale.compose.demo.effect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Created by Ronak Harkhani on 21/10/24
 */

@Composable
fun GlowingEffect(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(48.dp, Alignment.CenterVertically),
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .glowingBorder(
                    containerColor = MaterialTheme.colorScheme.background,
                    glowColor = MaterialTheme.colorScheme.primary,
                    cornerRadius = 16.dp,
                )
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .glowingBorder(
                    containerColor = MaterialTheme.colorScheme.background,
                    glowColor = MaterialTheme.colorScheme.tertiary,
                    cornerRadius = 50.dp,
                )
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .aspectRatio(1 / 1.1547005f)
                .glowingBorder(
                    containerColor = MaterialTheme.colorScheme.background,
                    glowColor = Color.Green,
                    pathFactory = {
                        Path().apply {
                            moveTo(size.width / 2f, 0f) // Start at top center
                            lineTo(size.width, size.height / 4f)
                            lineTo(size.width, size.height * 3 / 4f)
                            lineTo(size.width / 2f, size.height)
                            lineTo(0f, size.height * 3 / 4f)
                            lineTo(0f, size.height / 4f)
                            close()
                        }
                    }
                )
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .glowingBorder(
                    shape = CutCornerShape(16.dp),
                    containerColor = MaterialTheme.colorScheme.background,
                    glowColor = Color.Cyan,
                )
        )
    }
}


private fun Modifier.glowingBorder(
    containerColor: Color,
    glowColor: Color,
    glowRadius: Dp = 16.dp,
    cornerRadius: Dp = 0.dp,
) = this.drawBehind {
    drawContext.canvas.apply {
        drawRoundRect(
            0f, 0f, size.width, size.height,
            cornerRadius.toPx(), cornerRadius.toPx(),
            Paint().apply {
                color = containerColor
                isAntiAlias = true
                // TODO: Find KMP equivalent for setShadowLayer
                // setShadowLayer(glowRadius.toPx(), 0f, 0f, glowColor.toArgb())
            }
        )
    }
}

private fun Modifier.glowingBorder(
    pathFactory: DrawScope.() -> Path,
    containerColor: Color,
    glowColor: Color,
    glowRadius: Dp = 16.dp,
) = this.drawBehind {
    drawContext.canvas.apply {
        drawPath(
            pathFactory(),
            Paint().apply {
                color = containerColor
                isAntiAlias = true
                // setShadowLayer(glowRadius.toPx(), 0f, 0f, glowColor.toArgb())
            }
        )
    }
}

private fun Modifier.glowingBorder(
    shape: Shape,
    containerColor: Color,
    glowColor: Color,
    glowRadius: Dp = 16.dp,
) = composed {
    val density = LocalDensity.current
    this.drawBehind {
        drawContext.canvas.apply {
            drawPath(
                Path().apply {
                    addOutline(shape.createOutline(size, LayoutDirection.Ltr, density))
                },
                Paint().apply {
                    color = containerColor
                    isAntiAlias = true
                    // setShadowLayer(glowRadius.toPx(), 0f, 0f, glowColor.toArgb())
                }
            )
        }
    }
}


@Preview
@Composable
private fun Preview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            GlowingEffect()
        }
    }
}
