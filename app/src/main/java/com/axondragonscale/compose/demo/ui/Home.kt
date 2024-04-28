package com.axondragonscale.compose.demo.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.axondragonscale.compose.demo.BuildConfig
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

/**
 * Created by Ronak Harkhani on 21/04/24
 */

private val DemoItems = listOf(
    Route.ParallaxCarousel,
    Route.ParallaxCarouselV2,
    Route.Typewriter,
    Route.Shimmer,
    Route.Loaders,
    Route.CircleLayout,
    Route.RadialList,
    Route.AnimatedBorder,
    Route.CardFlip,
    Route.FluidFab,
    Route.Counter,
    Route.CubePager,
    Route.CircularRevealPager,
)

@Composable
fun Home(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 36.dp)
                .padding(horizontal = 16.dp),
            text = "Compose Demos",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
        )

        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 4.dp, bottom = 24.dp)
                .padding(horizontal = 20.dp),
            text = "App Version: ${BuildConfig.VERSION_NAME}",
            style = MaterialTheme.typography.labelMedium
        )

        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { Spacer(modifier = Modifier.height(0.dp)) }
                items(DemoItems) {
                    ListItem(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { navController.navigate(it.route) },
                        headlineContent = {
                            Text(
                                text = it.title,
                                fontWeight = FontWeight.Bold,
                            )
                        },
                        supportingContent = {
                            Text(
                                modifier = Modifier.padding(top = 4.dp),
                                text = it.description,
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        trailingContent = {
                            Icon(
                                modifier = Modifier.offset(8.dp),
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null
                            )
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            headlineColor = MaterialTheme.colorScheme.primary,
                            supportingColor = MaterialTheme.colorScheme.secondary,
                            trailingIconColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
                item { Spacer(modifier = Modifier.height(0.dp)) }
            }
        }
    }
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomePreview() {
    ComposeDemosTheme {
        Surface {
            Home(
                modifier = Modifier,
                navController = rememberNavController(),
            )
        }
    }
}
