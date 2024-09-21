package com.axondragonscale.compose.demo.button

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateBounds
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

/**
 * Created by Ronak Harkhani on 22/09/24
 */

@Composable
fun SegmentedButton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        val shape1 = RectangleShape
        SegmentedButtonContainer(
            modifier = Modifier.fillMaxWidth(0.9f).clip(shape1),
            shape = shape1
        ) {
            var selectedIndex by remember { mutableIntStateOf(0) }
            listOf("One", "Two", "Three").forEachIndexed { index, text ->
                SegmentedButton(
                    text = text,
                    isSelected = index == selectedIndex,
                    onClick = { selectedIndex = index },
                    shape = shape1
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        val shape2 = RoundedCornerShape(12.dp)
        SegmentedButtonContainer(
            modifier = Modifier.fillMaxWidth(0.9f).clip(shape2),
            shape = shape2
        ) {
            var selectedIndex by remember { mutableIntStateOf(0) }
            listOf("One", "Two", "Three").forEachIndexed { index, text ->
                SegmentedButton(
                    text = text,
                    isSelected = index == selectedIndex,
                    onClick = { selectedIndex = index },
                    shape = shape2
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        val shape3 = CircleShape
        SegmentedButtonContainer(
            modifier = Modifier.fillMaxWidth(0.9f).clip(shape3),
            shape = shape3
        ) {
            var selectedIndex by remember { mutableIntStateOf(-1) }
            listOf("One", "Two", "Three").forEachIndexed { index, text ->
                SegmentedButton(
                    text = text,
                    isSelected = index == selectedIndex,
                    onClick = { selectedIndex = index },
                    shape = shape3
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        val shape4 = CutCornerShape(12.dp)
        SegmentedButtonContainer(
            modifier = Modifier.fillMaxWidth(0.9f).clip(shape4),
            shape = shape4
        ) {
            var selectedIndex by remember { mutableIntStateOf(-1) }
            listOf("One", "Two", "Three").forEachIndexed { index, text ->
                SegmentedButton(
                    text = text,
                    isSelected = index == selectedIndex,
                    onClick = { selectedIndex = index },
                    shape = shape4
                )
            }
        }
    }
}

private const val SELECTED_BACKGROUND_ID = "SELECTED_BACKGROUND_ID"
private const val SELECTED_BUTTON_ID = "SELECTED_BUTTON_ID"

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SegmentedButtonContainer(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary.copy(0.1f),
    ) {
        LookaheadScope {
            Layout(
                modifier = Modifier.height(IntrinsicSize.Max),
                content = {
                    SelectedBackground(
                        modifier = Modifier.animateBounds(this),
                        shape = shape
                    )

                    content()
                },
            ) { measurables, constraints ->
                // Find equal width for buttons (Filter out the selected background measurable)
                val buttonMeasurables = measurables.filter { it.layoutId != SELECTED_BACKGROUND_ID }
                val buttonWidth = constraints.maxWidth / buttonMeasurables.size
                val buttonConstraints = constraints.copy(minWidth = buttonWidth, maxWidth = buttonWidth)
                val buttonPlaceables = buttonMeasurables.map { it.measure(buttonConstraints) }

                // Measure background if any button is selected
                val selectedButtonIndex = buttonMeasurables.indexOfFirst { it.layoutId == SELECTED_BUTTON_ID }
                val selectedBackgroundMeasurable = measurables.first { it.layoutId == SELECTED_BACKGROUND_ID }
                val selectedBackgroundPlaceable = if (selectedButtonIndex != -1) selectedBackgroundMeasurable.measure(buttonConstraints) else null

                layout(
                    width = buttonPlaceables.sumOf { it.width },
                    height = buttonPlaceables.maxOf { it.height },
                ) {
                    selectedBackgroundPlaceable?.placeRelative(
                        x = (selectedButtonIndex) * buttonWidth,
                        y = 0,
                    )

                    buttonPlaceables.forEachIndexed { index, placeable ->
                        placeable.placeRelative(
                            x = index * buttonWidth,
                            y = 0,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SegmentedButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    shape: Shape,
) {
    Box(
        modifier = modifier
            .then(if (isSelected) Modifier.layoutId(SELECTED_BUTTON_ID) else Modifier)
            .widthIn(min = 64.dp)
            .height(48.dp)
            .clip(shape)
            .selectable(selected = isSelected, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = text,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SelectedBackground(
    modifier: Modifier = Modifier,
    shape: Shape
) {
    Surface(
        modifier = modifier.layoutId(SELECTED_BACKGROUND_ID),
        color = MaterialTheme.colorScheme.surface,
        shape = shape,
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary),
    ) {}
}


@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ComposeDemosTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SegmentedButton()
        }
    }
}
