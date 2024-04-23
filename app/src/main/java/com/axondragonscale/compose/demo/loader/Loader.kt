package com.axondragonscale.compose.demo.loader

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

/**
 * Created by Ronak Harkhani on 23/04/24
 */

@Composable
fun Loaders(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LoaderContainer(Modifier.height(48.dp), Alignment.BottomCenter) {
            BouncingCirclesLoader()
        }
        Spacer(modifier = Modifier.height(24.dp))
        LoaderContainer(Modifier.height(48.dp)) {
            PulsingCirclesLoader()
        }
        Spacer(modifier = Modifier.height(24.dp))
        LoaderContainer(Modifier.height(48.dp)) {
            FlashingCirclesLoader()
        }
        Spacer(modifier = Modifier.height(24.dp))
        LoaderContainer(Modifier.height(48.dp)) {
            NewtonCradleLoader()
        }
    }
}

@Composable
private fun LoaderContainer(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            .padding(horizontal = 24.dp, vertical = 8.dp),
        contentAlignment = contentAlignment,
        content = content
    )
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LoaderPreview() {
    ComposeDemosTheme {
        Surface {
            Loaders()
        }
    }
}
