@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ebaykleinanzeigenzakir.window.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ebaykleinanzeigenzakir.EbayViewModel
import com.example.ebaykleinanzeigenzakir.MainItemData
import kotlinx.coroutines.launch


@Composable
fun MainItem(navController: NavHostController, item: MainItemData, viewModel: EbayViewModel) {
    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .shadow(2.dp, ambientColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),shape= RoundedCornerShape(2))
            .padding(0.dp)
            .width(170.dp)
            .clickable {
                viewModel.activeAddLink = item.url_link
                viewModel.getAddData()
                navController.navigate("add")
            },
        border =BorderStroke(0.5.dp,MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)),
//        elevation = CardDefaults.cardElevation(2.dp,) ,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface,),
        shape = RoundedCornerShape(2)
    ) {
        Column() {

            Box(
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {

                var loaded by remember { mutableStateOf(false) }
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(item.image_link).crossfade(true).build(),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,

                    onSuccess = { loaded = true },
                    contentDescription = null
                )
                if (!loaded) {
                    Icon(
                        imageVector = Icons.Filled.PhotoCamera,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)

                    )
                }

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
