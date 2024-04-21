package com.axondragonscale.compose.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.axondragonscale.compose.demo.ui.DemoApp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemosTheme {
                Surface {
                    DemoApp()
                }
            }
        }
    }
}
