package com.axondragonscale.compose.demo.typewriter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.random.nextLong

/**
 * Created by Ronak Harkhani on 21/04/24
 */

@Composable
fun Modifier.typewriterEffect(
    text: String,
    onTextUpdate: (AnnotatedString) -> Unit,
    onEffectComplete: (suspend () -> Unit)? = null,
    chunkRange: IntRange = 1.rangeTo(2),
    delayRange: LongRange = 16L.rangeTo(64L),
): Modifier {
    LaunchedEffect(text) {
        if (text.isBlank()) return@LaunchedEffect

        var currentSize = 0
        while (currentSize < text.length) {
            val currentChunk = Random.nextInt(chunkRange)
            val currentDelay = Random.nextLong(delayRange)
            currentSize = minOf(currentSize + currentChunk, text.length)

            val displayText = buildAnnotatedString {
                append(text.take(currentSize))
                // Reserves the space required to render the remaining text
                append(
                    AnnotatedString(
                        text = text.takeLast(text.length - currentSize),
                        spanStyle = SpanStyle(color = Color.Transparent)
                    )
                )
            }

            onTextUpdate.invoke(displayText)
            delay(currentDelay)
        }
        onEffectComplete?.invoke()
    }

    return this
}
