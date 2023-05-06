package com.example.ebaykleinanzeigenzakir.window.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ebaykleinanzeigenzakir.EbayViewModel
import com.example.ebaykleinanzeigenzakir.R
import com.example.ebaykleinanzeigenzakir.SearchResults

import com.example.ebaykleinanzeigenzakir.ui.theme.EbayKleinanzeigenZakirTheme

@Composable
internal fun SearchItem(
    navController: NavHostController,
    item: SearchResults,
    viewModel: EbayViewModel
) {

    Row(
        Modifier
            .height(120.dp)
            .fillMaxWidth()
            .clickable {
                viewModel.activeAddLink = item.url_link
                viewModel.getAddData()
                navController.navigate("add")
            }
            .background(color = MaterialTheme.colorScheme.surface)

    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(162.dp)
                .padding(0.dp, 1.dp), contentAlignment = Alignment.Center
        ) {
            var imageLoaded by remember { mutableStateOf(false) }


            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.image_url).crossfade(true).build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                onSuccess = { imageLoaded = true }
            )
            if (!imageLoaded) {
                Icon(
                    imageVector = Icons.Filled.PhotoCamera,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                )
            }
        }
        Column(
            Modifier
                .fillMaxHeight()
                .weight(1f)
                .border(1.dp, color = MaterialTheme.colorScheme.background)
                .padding(start = 10.dp, top = 5.dp, bottom = 10.dp)
        ) {
            Text(
                text = item.location, color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = item.title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = item.price,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.weight(1f))


            Text(
                text = "Versand möglich",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        RoundedCornerShape(50)
                    )
                    .padding(6.dp, 2.dp),

                )
        }
    }
}

//
//@Preview
//@Composable
//fun SeeSearchItem() {
//    val navController = rememberNavController()
//    EbayKleinanzeigenZakirTheme {
//        SearchItem(navController, it, viewModel)
//    }
//}