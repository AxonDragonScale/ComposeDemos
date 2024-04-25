package com.axondragonscale.compose.demo.radial

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

/**
 * Created by Ronak Harkhani on 25/04/24
 */

@Composable
fun RadialList(modifier: Modifier = Modifier) {
    // TODO
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RadialListPreview() {
    ComposeDemosTheme {
        Surface {
            RadialList()
        }
    }
}
