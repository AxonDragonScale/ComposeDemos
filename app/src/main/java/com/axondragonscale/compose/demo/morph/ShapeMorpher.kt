package com.axondragonscale.compose.demo.morph

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.TransformResult
import androidx.graphics.shapes.circle
import androidx.graphics.shapes.star
import androidx.graphics.shapes.toPath
import androidx.graphics.shapes.transformed
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
import kotlin.math.min

val shapes = listOf(
    RoundedPolygon.circle(
        numVertices = 4,
        radius = 1f,
        centerX = 0f,
        centerY = 0f,
    ).normalized(),

    RoundedPolygon.star(
        numVerticesPerRadius = 12,
        innerRadius = 0.9f,
        rounding = CornerRounding(0.1f),
        innerRounding = CornerRounding(0.1f)
    ).normalized(),

    RoundedPolygon.star(
        numVerticesPerRadius = 4,
        innerRadius = 0.35f,
        rounding = CornerRounding(0.3f),
        innerRounding = CornerRounding(0.3f),
    ).normalized()
)

@Composable
internal fun ShapeMorpher(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        ShapeList()

        Controls(
            modifier = Modifier.padding(top = 8.dp)
        )

        MorphBox(
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ShapeList(modifier: Modifier = Modifier) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        maxItemsInEachRow = 5
    ) {
        shapes.forEach { shape ->
            val borderAlpha = 0f
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(4.dp)
                    .border(
                        width = 2.dp,
                        color = LocalContentColor.current.copy(alpha = borderAlpha),
                        shape = RoundedCornerShape(4.dp)
                    )
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
) {
    val color = LocalContentColor.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .drawWithContent {
                drawContent()

                val scale = min(size.width, size.height)
                drawPath(
                    path = shape
                        .fitToBox(scale)
                        .toPath()
                        .asComposePath(),
                    color = color
                )
            }
    )
}

fun RoundedPolygon.fitToBox(scale: Float) = this.transformed { x, y ->
//    val matrix = Matrix().apply { scale(x = scale, y = scale) }
//
//    val transformedPoint = matrix.map(Offset(x, y))
    TransformResult(x * scale, y * scale)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColumnScope.Controls(modifier: Modifier = Modifier) {
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

        SingleChoiceSegmentedButtonRow {
            var drawType by remember { mutableStateOf(DrawType.Shape) }
            DrawType.entries.forEach {
                SegmentedButton(
                    selected = drawType == it,
                    onClick = { drawType = it },
                    shape = SegmentedButtonDefaults.itemShape(
                        drawType.ordinal,
                        2,
                        RoundedCornerShape(4.dp)
                    ),
                    icon = { },
                    label = { Text(it.name) }
                )
            }
        }
    }

    var morphProgress by remember { mutableFloatStateOf(0f) }
    Slider(
        modifier = Modifier.padding(horizontal = 8.dp),
        value = morphProgress,
        onValueChange = { morphProgress = it },
    )
}

@Composable
private fun MorphBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .border(1.dp, Color.Red)
            .drawWithContent {
                drawContent()

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
