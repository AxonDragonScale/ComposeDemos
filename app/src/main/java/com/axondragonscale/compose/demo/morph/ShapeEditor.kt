package com.axondragonscale.compose.demo.morph

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

@Composable
internal fun ShapeEditor(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {

    }
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MorphPreview() {
    ComposeDemosTheme {
        Surface {
            ShapeEditor()
        }
    }
}
