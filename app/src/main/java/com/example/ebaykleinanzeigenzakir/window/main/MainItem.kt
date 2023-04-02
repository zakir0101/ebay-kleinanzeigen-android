@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ebaykleinanzeigenzakir

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch


@Composable
fun MainItem(navController: NavHostController, item: MainItemData) {
    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .padding(0.dp)
            .width(150.dp)
            .clickable { scope.launch { navController.navigate("add") }  },
        shape = RoundedCornerShape(0),
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column() {

            Box(modifier = Modifier
                .height(130.dp)
                .fillMaxWidth() , contentAlignment = Alignment.Center) {

                var loading by remember { mutableStateOf(true) }
                val modifier = if (loading) Modifier.size(48.dp) else Modifier.fillMaxSize()

                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(item.image_link).crossfade(true).build(),
                    modifier = modifier,
                    contentScale = ContentScale.FillBounds,
                    placeholder = painterResource(id = R.drawable.camera),
                    onSuccess = { loading = false },
                            contentDescription = null
                )

            }
            Column(modifier = Modifier.padding(top = 5.dp, start = 10.dp)) {
                val location = when (item.location.length) {
                    in 0..19 -> item.location
                    else -> item.location.substring(0..19) + "..."
                }

                Text(
                    location,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                val title = when (item.title.length) {
                    in 0..15 -> item.title
                    else -> item.title.substring(0..15) + "..."
                }
                val titleSm = MaterialTheme.typography.titleSmall
                val titleSmaller = titleSm.copy(fontSize = titleSm.fontSize * 0.9f)
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = titleSmaller
                )
                Text(
                    item.price,
                    modifier = Modifier.padding(top = 2.dp, bottom = 2.dp),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall
                )
            }

        }
    }
}
