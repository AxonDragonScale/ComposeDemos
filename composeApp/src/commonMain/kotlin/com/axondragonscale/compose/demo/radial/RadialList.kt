package com.axondragonscale.compose.demo.radial

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Created by Ronak Harkhani on 25/04/24
 */

@Composable
fun RadialList(modifier: Modifier = Modifier) {
    // TODO
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "TODO")
    }
}

@Preview
@Composable
private fun Preview() {
    MaterialTheme {
        Surface {
            RadialList()
        }
    }
}
