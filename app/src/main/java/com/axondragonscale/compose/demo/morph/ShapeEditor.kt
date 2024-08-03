package com.axondragonscale.compose.demo.morph

import android.content.res.Configuration
import androidx.annotation.IntRange
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.circle
import androidx.graphics.shapes.pill
import androidx.graphics.shapes.pillStar
import androidx.graphics.shapes.rectangle
import androidx.graphics.shapes.star
import androidx.graphics.shapes.toPath
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
import kotlin.math.min

private enum class BaseShape {
    Circle,
    Rectangle,
    Star,
    Pill,
    PillStar,
}

private enum class CornerRoundness {
    Uniform,
    PerVertex,
}

// This can crash when moving sliders around
// New change occurring while previous is not complete seems to make compose think that the
// shape is not contiguous

@Composable
internal fun ShapeEditor(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val shape = remember { mutableStateOf(RoundedPolygon.circle()) }
        val drawType = remember { mutableStateOf(DrawType.Shape) }
        ShapeBox(shape = shape.value, drawType = drawType.value)
        Spacer(modifier = Modifier.height(8.dp))
        ShapeControls(shape = shape, drawType = drawType)
    }
}

@Composable
private fun ShapeControls(
    modifier: Modifier = Modifier,
    shape: MutableState<RoundedPolygon>,
    drawType: MutableState<DrawType>,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(4.dp)
            )
            .verticalScroll(state = rememberScrollState())
            .padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var baseShape by remember { mutableStateOf(BaseShape.PillStar) }
        BaseShapeSelector(
            baseShape = baseShape,
            onBaseShapeChange = { baseShape = it }
        )

        DrawTypeSelector(drawType = drawType)

        when (baseShape) {
            BaseShape.Circle -> CircleShapeControls(shape = shape)
            BaseShape.Rectangle -> RectangleShapeControls(shape = shape)
            BaseShape.Star -> StarShapeControls(shape = shape)
            BaseShape.Pill -> PillShapeControls(shape = shape)
            BaseShape.PillStar -> PillStarShapeControls(shape = shape)
        }
    }
}

@Composable
private fun CircleShapeControls(shape: MutableState<RoundedPolygon>) {
    Text(text = "No controls available for Circle Shape")
    LaunchedEffect(Unit) {
        shape.value = RoundedPolygon.circle()
    }
}

@Composable
private fun RectangleShapeControls(shape: MutableState<RoundedPolygon>) {
    var width by remember { mutableFloatStateOf(1f) }
    var height by remember { mutableFloatStateOf(1f) }
    val cornerRoundness = remember { mutableStateOf(CornerRoundness.Uniform) }
    var cornerRadius by remember { mutableFloatStateOf(0f) }
    var cornerSmoothing by remember { mutableFloatStateOf(0f) }
    var v1CornerRadius by remember { mutableFloatStateOf(0f) }
    var v1CornerSmoothing by remember { mutableFloatStateOf(0f) }
    var v2CornerRadius by remember { mutableFloatStateOf(0f) }
    var v2CornerSmoothing by remember { mutableFloatStateOf(0f) }
    var v3CornerRadius by remember { mutableFloatStateOf(0f) }
    var v3CornerSmoothing by remember { mutableFloatStateOf(0f) }
    var v4CornerRadius by remember { mutableFloatStateOf(0f) }
    var v4CornerSmoothing by remember { mutableFloatStateOf(0f) }

    val updateShape = {
        shape.value = RoundedPolygon.rectangle(
            width = width,
            height = height,
            rounding =
            if (cornerRoundness.value == CornerRoundness.Uniform)
                CornerRounding(radius = cornerRadius, smoothing = cornerSmoothing)
            else
                CornerRounding.Unrounded,
            perVertexRounding = if (cornerRoundness.value == CornerRoundness.Uniform) null
            else listOf(
                CornerRounding(radius = v1CornerRadius, smoothing = v1CornerSmoothing),
                CornerRounding(radius = v2CornerRadius, smoothing = v2CornerSmoothing),
                CornerRounding(radius = v3CornerRadius, smoothing = v3CornerSmoothing),
                CornerRounding(radius = v4CornerRadius, smoothing = v4CornerSmoothing)
            )
        )
    }

    LaunchedEffect(
        width,
        height,
        cornerRoundness,
        cornerRadius,
        cornerSmoothing,
        v1CornerRadius,
        v1CornerSmoothing,
        v2CornerRadius,
        v2CornerSmoothing,
        v3CornerRadius,
        v3CornerSmoothing,
        v4CornerRadius,
        v4CornerSmoothing
    ) {
        updateShape()
    }

    BaseSlider(title = "Width", value = width, onValueChange = { width = it })
    BaseSlider(title = "Height", value = height, onValueChange = { height = it })

    CornerRoundnessSelector(cornerRoundness = cornerRoundness)
    if (cornerRoundness.value == CornerRoundness.Uniform) {
        BaseSlider(
            title = "Corner Radius",
            value = cornerRadius,
            onValueChange = { cornerRadius = it }
        )
        BaseSlider(
            title = "Corner Smoothing",
            value = cornerSmoothing,
            onValueChange = { cornerSmoothing = it }
        )
    } else {
        BaseSlider(
            title = "V1 Corner Radius",
            value = v1CornerRadius,
            onValueChange = { v1CornerRadius = it }
        )
        BaseSlider(
            title = "V1 Corner Smoothing",
            value = v1CornerSmoothing,
            onValueChange = { v1CornerSmoothing = it }
        )
        BaseSlider(
            title = "V2 Corner Radius",
            value = v2CornerRadius,
            onValueChange = { v2CornerRadius = it }
        )
        BaseSlider(
            title = "V2 Corner Smoothing",
            value = v2CornerSmoothing,
            onValueChange = { v2CornerSmoothing = it }
        )
        BaseSlider(
            title = "V3 Corner Radius",
            value = v3CornerRadius,
            onValueChange = { v3CornerRadius = it }
        )
        BaseSlider(
            title = "V3 Corner Smoothing",
            value = v3CornerSmoothing,
            onValueChange = { v3CornerSmoothing = it }
        )
        BaseSlider(
            title = "V4 Corner Radius",
            value = v4CornerRadius,
            onValueChange = { v4CornerRadius = it }
        )
        BaseSlider(
            title = "V4 Corner Smoothing",
            value = v4CornerSmoothing,
            onValueChange = { v4CornerSmoothing = it }
        )
    }
}

@Composable
private fun StarShapeControls(shape: MutableState<RoundedPolygon>) {
    var vertices by remember { mutableIntStateOf(6) }
    var radius by remember { mutableFloatStateOf(1f) }
    var innerRadius by remember { mutableFloatStateOf(0.9f) }
    var rounding by remember { mutableFloatStateOf(0f) }
    var smoothing by remember { mutableFloatStateOf(0f) }
    var innerRounding by remember { mutableFloatStateOf(0f) }
    var innerSmoothing by remember { mutableFloatStateOf(0f) }

    val updateShape = {
        shape.value = RoundedPolygon.star(
            numVerticesPerRadius = vertices,
            radius = radius,
            innerRadius = innerRadius.coerceAtMost(radius - 0.01f),
            rounding = CornerRounding(radius = rounding, smoothing = smoothing),
            innerRounding = CornerRounding(radius = innerRounding, smoothing = innerSmoothing)
        )
    }

    LaunchedEffect(
        vertices,
        radius,
        innerRadius,
        rounding,
        smoothing,
        innerRounding,
        innerSmoothing
    ) {
        updateShape()
    }

    BaseSlider(
        title = "Vertices",
        value = vertices.toFloat(),
        onValueChange = { vertices = it.toInt() },
        valueRange = 2f..20f,
        steps = 18
    )

    BaseSlider(
        title = "Radius",
        value = radius,
        onValueChange = { radius = it }
    )
    BaseSlider(
        title = "Inner Radius",
        value = innerRadius,
        onValueChange = { innerRadius = it }
    )
    BaseSlider(
        title = "Rounding",
        value = rounding,
        onValueChange = { rounding = it }
    )
    BaseSlider(
        title = "Smoothing",
        value = smoothing,
        onValueChange = { smoothing = it }
    )
    BaseSlider(
        title = "Inner Rounding",
        value = innerRounding,
        onValueChange = { innerRounding = it }
    )
    BaseSlider(
        title = "Inner Smoothing",
        value = innerSmoothing,
        onValueChange = { innerSmoothing = it }
    )

}

@Composable
fun PillShapeControls(shape: MutableState<RoundedPolygon>) {
    var width by remember { mutableFloatStateOf(1f) }
    var height by remember { mutableFloatStateOf(0.5f) }
    var smoothing by remember { mutableFloatStateOf(0f) }

    val updateShape = {
        shape.value = RoundedPolygon.pill(
            width = width,
            height = height,
            smoothing = smoothing,
        )
    }

    LaunchedEffect(width, height, smoothing) {
        updateShape()
    }

    BaseSlider(title = "Width", value = width, onValueChange = { width = it })
    BaseSlider(title = "Height", value = height, onValueChange = { height = it })
    BaseSlider(title = "Smoothing", value = smoothing, onValueChange = { smoothing = it })
}

@Composable
private fun PillStarShapeControls(shape: MutableState<RoundedPolygon>) {
    var vertices by remember { mutableIntStateOf(6) }
    var width by remember { mutableFloatStateOf(1f) }
    var height by remember { mutableFloatStateOf(0.5f) }
    var innerRadiusRatio by remember { mutableFloatStateOf(0.5f) }
    var vertexSpacing by remember { mutableFloatStateOf(0.5f) }
    var rounding by remember { mutableFloatStateOf(0f) }
    var smoothing by remember { mutableFloatStateOf(0f) }
    var innerRounding by remember { mutableFloatStateOf(0f) }
    var innerSmoothing by remember { mutableFloatStateOf(0f) }

    val updateShape = {
        shape.value = RoundedPolygon.pillStar(
            numVerticesPerRadius = vertices,
            width = width,
            height = height,
            innerRadiusRatio = innerRadiusRatio,
            vertexSpacing = vertexSpacing,
            rounding = CornerRounding(radius = rounding, smoothing = smoothing),
            innerRounding = CornerRounding(radius = innerRounding, smoothing = innerSmoothing)
        )
    }

    LaunchedEffect(
        vertices,
        width,
        height,
        innerRadiusRatio,
        vertexSpacing,
        rounding,
        smoothing,
        innerRounding,
        innerSmoothing
    ) {
        updateShape()
    }

    BaseSlider(
        title = "Vertices",
        value = vertices.toFloat(),
        onValueChange = { vertices = it.toInt() },
        valueRange = 2f..20f,
        steps = 18
    )

    BaseSlider(
        title = "Width",
        value = width,
        onValueChange = { width = it }
    )
    BaseSlider(
        title = "Height",
        value = height,
        onValueChange = { height = it }
    )
    BaseSlider(
        title = "Inner Radius Ratio",
        value = innerRadiusRatio,
        onValueChange = { innerRadiusRatio = it }
    )
    BaseSlider(
        title = "Vertex Spacing",
        value = vertexSpacing,
        onValueChange = { vertexSpacing = it }
    )
    BaseSlider(
        title = "Rounding",
        value = rounding,
        onValueChange = { rounding = it }
    )
    BaseSlider(
        title = "Smoothing",
        value = smoothing,
        onValueChange = { smoothing = it }
    )
    BaseSlider(
        title = "Inner Rounding",
        value = innerRounding,
        onValueChange = { innerRounding = it }
    )
    BaseSlider(
        title = "Inner Smoothing",
        value = innerSmoothing,
        onValueChange = { innerSmoothing = it }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BaseShapeSelector(
    modifier: Modifier = Modifier,
    baseShape: BaseShape,
    onBaseShapeChange: (BaseShape) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Base Shape")

        Spacer(modifier = Modifier.width(16.dp))

        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {

            BasicTextField(
                modifier = Modifier
                    .defaultMinSize(minHeight = 32.dp)
                    .fillMaxWidth()
                    .menuAnchor(),
                value = baseShape.name,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        innerTextField()
                        Spacer(modifier = Modifier.weight(1f))
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                }
            )

            DropdownMenu(
                modifier = Modifier.exposedDropdownSize(),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                BaseShape.entries.forEach {
                    DropdownMenuItem(
                        text = { Text(it.name) },
                        onClick = {
                            onBaseShapeChange(it)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawTypeSelector(
    modifier: Modifier = Modifier,
    drawType: MutableState<DrawType>,
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier.height(32.dp)) {
        Text(text = "Draw Type")
        Spacer(modifier = Modifier.width(16.dp))
        DrawType.entries.forEach {
            SegmentedButton(
                modifier = Modifier.height(32.dp),
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
                label = {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CornerRoundnessSelector(
    modifier: Modifier = Modifier,
    cornerRoundness: MutableState<CornerRoundness>,
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier.height(32.dp)) {
        Text(text = "Corner Roundness")
        Spacer(modifier = Modifier.width(16.dp))
        CornerRoundness.entries.forEach {
            SegmentedButton(
                modifier = Modifier.height(32.dp),
                selected = cornerRoundness.value == it,
                onClick = { cornerRoundness.value = it },
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
                label = {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            )
        }
    }
}

@Composable
private fun BaseSlider(
    modifier: Modifier = Modifier,
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0.001f..1f,
    @IntRange(from = 0) steps: Int = 0,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = title)
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps,
        )
    }
}

@Composable
private fun ShapeBox(
    modifier: Modifier = Modifier,
    shape: RoundedPolygon,
    drawType: DrawType,
    color: Color = LocalContentColor.current,
) {
    Box(modifier = modifier.aspectRatio(1f)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(4.dp)
                )
                .drawWithContent {
                    drawContent()

                    val scale = min(size.width, size.height) * 0.95f
                    if (drawType == DrawType.Shape) {
                        val path = shape
                            .normalized()
                            .scale(scale)
                            .recenter(center)
                            .toPath()
                            .asComposePath()
                        drawPath(path = path, color = color)
                    } else if (drawType == DrawType.Bezier) {
                        drawBeziers(shape.normalized(), scale, color)
                    }
                }
        )
    }
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ComposeDemosTheme {
        Surface {
            ShapeEditor()
        }
    }
}
