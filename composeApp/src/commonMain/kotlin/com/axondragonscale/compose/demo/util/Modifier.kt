package com.axondragonscale.compose.demo.util

import androidx.compose.ui.Modifier

/**
 * Created by Ronak Harkhani on 19/10/24
 */

fun Modifier.thenIf(predicate: Boolean, modifier: Modifier.() -> Modifier) =
    if (predicate) this.modifier() else this
