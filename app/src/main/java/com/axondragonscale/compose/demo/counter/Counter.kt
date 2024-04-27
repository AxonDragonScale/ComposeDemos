package com.axondragonscale.compose.demo.counter

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

/**
 * Created by Ronak Harkhani on 27/04/24
 */

@Composable
fun Counter(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        CounterCard(modifier = Modifier.weight(1f)) {
            var number by remember { mutableStateOf(0) }
            AnimatedCounter(
                modifier = Modifier.height(100.dp),
                number = number,
                drawChar = { char ->
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = "$char",
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            )
            Spacer(modifier = Modifier.height(48.dp))
            Row {
                FilledIconButton(onClick = { number-- }) {
                    Icon(imageVector = Icons.Default.Remove, contentDescription = null)
                }
                Spacer(modifier = Modifier.width(48.dp))
                FilledIconButton(onClick = { number++ }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        }

        Spacer(modifier = Modifier.height(64.dp))

        CounterCard(modifier = Modifier.weight(1f)) {
            var number by remember { mutableStateOf(0.0) }
            AnimatedCounter(
                modifier = Modifier.height(100.dp),
                number = number,
                convertToString = { String.format("%.2f", it) },
                drawChar = { char ->
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = "$char",
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            )
            Spacer(modifier = Modifier.height(48.dp))
            ControlButtons(
                onDecrement = { number -= 0.01 },
                onIncrement = { number += 0.01 }
            )
        }

        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
private fun CounterCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content,
    )
}

@Composable
private fun ControlButtons(
    modifier: Modifier = Modifier,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
) {
    Row(modifier = modifier) {
        FilledIconButton(onClick = onDecrement) {
            Icon(imageVector = Icons.Default.Remove, contentDescription = null)
        }
        Spacer(modifier = Modifier.width(48.dp))
        FilledIconButton(onClick = onIncrement) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CounterPreview() {
    ComposeDemosTheme {
        Surface {
            Counter()
        }
    }
}
