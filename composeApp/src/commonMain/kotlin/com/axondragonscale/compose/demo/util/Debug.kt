package com.axondragonscale.compose.demo.util

import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Created by Ronak Harkhani on 30/09/24
 */

fun Modifier.debugBorder(color: Color = Color.Red): Modifier =
    this.border(width = 1.dp, color = color)
