package com.axondragonscale.compose.demo.morph

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

@Composable
internal fun ShapeMorpher(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .offset(50.dp, 50.dp)
                .size(48.dp)
                .aspectRatio(1f)
                .drawWithContent {
                    val roundedPolygon = RoundedPolygon(
                        numVertices = 6,
                        radius = size.width / 2,
                        centerX = size.width / 2,
                        centerY = size.height / 2,
                    )
                    drawPath(roundedPolygon.toPath().asComposePath(), Color.Red)
                }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShapeList(modifier: Modifier = Modifier) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        maxItemsInEachRow = 5
    ) {

    }
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MorphPreview() {
    ComposeDemosTheme {
        Surface {
            ShapeMorpher()
        }
    }
}
