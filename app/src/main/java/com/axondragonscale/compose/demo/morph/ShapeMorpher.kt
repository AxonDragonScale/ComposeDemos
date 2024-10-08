package com.axondragonscale.compose.demo.morph

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.circle
import androidx.graphics.shapes.pill
import androidx.graphics.shapes.star
import androidx.graphics.shapes.toPath
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
import kotlinx.coroutines.launch
import kotlin.math.min

val shapes = listOf(
    RoundedPolygon
        .circle(
            numVertices = 4,
            radius = 1f,
            centerX = 0f,
            centerY = 0f,
        )
        .normalized(),

    RoundedPolygon
        .star(
            numVerticesPerRadius = 12,
            innerRadius = 0.9f,
            rounding = CornerRounding(0.1f),
            innerRounding = CornerRounding(0.1f)
        )
        .normalized(),

    RoundedPolygon
        .star(
            numVerticesPerRadius = 4,
            innerRadius = 0.30f,
            rounding = CornerRounding(0.3f),
            innerRounding = CornerRounding(0.3f),
        )
        .rotate(45f)
        .normalized(),

    RoundedPolygon
        .star(
            numVerticesPerRadius = 8,
            innerRadius = 0.8f,
            rounding = CornerRounding(0.15f),
            innerRounding = CornerRounding(0.15f),
        )
        .normalized(),

    RoundedPolygon
        .star(
            numVerticesPerRadius = 15,
            innerRadius = 0.8f,
            rounding = CornerRounding(1f),
            innerRounding = CornerRounding(1f),
        )
        .normalized(),

    RoundedPolygon
        .pill(width = 4f, height = 1f)
        .rotate(-45f)
        .normalized(),

    RoundedPolygon
        .pill(width = 4f, height = 1f)
        .rotate(45f)
        .normalized(),

    RoundedPolygon(
        numVertices = 3,
        rounding = CornerRounding(0.2f),
    )
        .rotate(-60f)
        .normalized(),

    RoundedPolygon(
        numVertices = 3,
        rounding = CornerRounding(0.2f),
    )
        .rotate(30f)
        .normalized(),

    RoundedPolygon(
        numVertices = 3,
        rounding = CornerRounding(0.2f),
    )
        .normalized(),

    RoundedPolygon(
        vertices = squareVertices(),
        perVertexRounding = listOf(
            CornerRounding(0.4f),
            CornerRounding(1f),
            CornerRounding(1f),
            CornerRounding(1f)
        )
    )
        .normalized(),

    RoundedPolygon(
        vertices = squareVertices(),
        perVertexRounding = listOf(
            CornerRounding(0.4f),
            CornerRounding(0.4f),
            CornerRounding(1f),
            CornerRounding(1f)
        )
    )
        .normalized(),

    RoundedPolygon(
        vertices = squareVertices(),
        perVertexRounding = listOf(
            CornerRounding(1f),
            CornerRounding(0.4f),
            CornerRounding(1f),
            CornerRounding(1f)
        )
    )
        .normalized(),

    RoundedPolygon(
        vertices = squareVertices(),
        perVertexRounding = listOf(
            CornerRounding(0.1f),
            CornerRounding(0.1f),
            CornerRounding(0.1f),
            CornerRounding(0.1f)
        )
    )
        .normalized(),

    RoundedPolygon(
        numVertices = 5,
        rounding = CornerRounding(0.2f),
    )
        .rotate(-18f)
        .normalized(),
)

@Composable
internal fun ShapeMorpher(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val morphProgress = remember { Animatable(0f) }
        val drawType = remember { mutableStateOf(DrawType.Shape) }
        val prevShape = remember { mutableStateOf(shapes[0]) }
        val currShape = remember { mutableStateOf(shapes[0]) }
        val morph by remember {
            derivedStateOf {
                Morph(start = prevShape.value, end = currShape.value)
            }
        }

        ShapeList(
            prevShape = prevShape,
            currShape = currShape,
            morphProgress = morphProgress,
        )

        Controls(
            modifier = Modifier.padding(top = 8.dp),
            drawType = drawType,
            morphProgress = morphProgress,
        )

        MorphBox(
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp),
            morph = morph,
            morphProgress = morphProgress.value,
            drawType = drawType.value,
        )
    }
}

@Composable
private fun ShapeList(
    modifier: Modifier = Modifier,
    prevShape: MutableState<RoundedPolygon>,
    currShape: MutableState<RoundedPolygon>,
    morphProgress: Animatable<Float, AnimationVector1D>,
) {
    val scope = rememberCoroutineScope()
    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Fixed(5),
    ) {
        items(shapes) { shape ->
            val borderAlpha = if (shape == currShape.value) 1f else 0f +
                    if (shape == prevShape.value) 1f - morphProgress.value else 0f

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                    .clickable {
                        scope.launch {
                            prevShape.value = currShape.value
                            currShape.value = shape

                            morphProgress.snapTo(0f)
                            morphProgress.animateTo(1f, spring(0.6f, 50f))
                        }
                    }
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary.copy(
                            alpha = borderAlpha.coerceIn(0f, 1f)
                        ),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(4.dp)
            ) {
                ShapeBox(shape = shape)
            }
        }
    }
}

@Composable
private fun ShapeBox(
    modifier: Modifier = Modifier,
    shape: RoundedPolygon,
    color: Color = LocalContentColor.current,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .drawWithContent {
                drawContent()

                val scale = min(size.width, size.height) * 0.95f
                val path = shape
                    .scale(scale)
                    .recenter(center)
                    .toPath()
                    .asComposePath()
                drawPath(path = path, color = color)
            }
    )
}

@Composable
private fun ColumnScope.Controls(
    modifier: Modifier = Modifier,
    drawType: MutableState<DrawType>,
    morphProgress: Animatable<Float, AnimationVector1D>,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            text = "Morph Progress",
            style = MaterialTheme.typography.labelLarge,
        )

        SingleChoiceSegmentedButtonRow(modifier = Modifier.height(36.dp)) {
            DrawType.entries.forEach {
                SegmentedButton(
                    selected = drawType.value == it,
                    onClick = { drawType.value = it },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = it.ordinal,
                        count = DrawType.entries.size,
                        baseShape = RoundedCornerShape(4.dp)
                    ),
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        activeContentColor = MaterialTheme.colorScheme.primary,
                        activeBorderColor = MaterialTheme.colorScheme.primary,
                    ),
                    icon = { },
                    label = { Text(it.name) }
                )
            }
        }
    }

    val scope = rememberCoroutineScope()
    Slider(
        modifier = Modifier.padding(horizontal = 8.dp).scale(1f, 0.75f),
        value = morphProgress.value,
        onValueChange = {
            scope.launch { morphProgress.snapTo(it) }
        },
    )
}

@Composable
private fun MorphBox(
    modifier: Modifier = Modifier,
    morph: Morph,
    morphProgress: Float,
    drawType: DrawType,
    color: Color = LocalContentColor.current
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(8.dp)
            .drawWithContent {
                drawContent()

                val scale = min(size.width, size.height)
                if (drawType == DrawType.Shape) {
                    drawShape(morph, morphProgress, scale, color)
                } else if (drawType == DrawType.Bezier) {
                    drawBeziers(morph, morphProgress, scale, color)
                }
            }
    )
}


@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ComposeDemosTheme {
        Surface {
            ShapeMorpher()
        }
    }
}
