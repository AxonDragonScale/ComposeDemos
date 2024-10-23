package com.axondragonscale.compose.demo.sensor

import android.content.res.Configuration
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
import com.axondragonscale.compose.demo.util.debugBorder
import kotlinx.coroutines.launch

/**
 * Created by Ronak Harkhani on 23/10/24
 */

@Composable
fun Accelerometer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            val maxOffsetX = this.maxWidth / 2
            val maxOffsetY = this.maxHeight / 2

            val context = LocalContext.current
            val scope = rememberCoroutineScope()
            val accX = remember { Animatable(0f) }
            val accY = remember { Animatable(0f) }

            DisposableEffect(Unit) {
                val sensorManager = context.getSystemService<SensorManager>()
                val accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
                val sensorEventListener = object: SensorEventListener {
                    override fun onSensorChanged(event: SensorEvent) {
                        scope.launch { accX.animateTo(-event.values[0] / 9.81f) }
                        scope.launch { accY.animateTo(event.values[1] / 9.81f) }
                    }

                    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                        // Ignore
                    }
                }
                sensorManager?.registerListener(
                    sensorEventListener,
                    accelerometer,
                    SensorManager.SENSOR_DELAY_GAME
                )

                onDispose {
                    sensorManager?.unregisterListener(sensorEventListener)
                }
            }

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .offset {
                        IntOffset(
                            x = (maxOffsetX.roundToPx() * accX.value).toInt(),
                            y = (maxOffsetY.roundToPx() * accY.value).toInt(),
                        )
                    }
                    .background(MaterialTheme.colorScheme.primary, CutCornerShape(12.dp))
            )
        }

        Text(
            modifier = Modifier.padding(8.dp),
            text = "Accelerometer",
        )
    }
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ComposeDemosTheme {
        Surface {
            Accelerometer()
        }
    }
}