package com.example.ebaykleinanzeigenzakir.window.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ebaykleinanzeigenzakir.EbayPlaceholder

@Composable
fun GalleryListPlaceholder() {
    LazyRow(
        contentPadding = PaddingValues(0.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(8) {
            MainItemPlaceholder()
        }

    }

}

@Composable
fun MainItemPlaceholder() {
    Card(
        modifier = Modifier
            .padding(0.dp)
            .width(170.dp)
            ,
        shape = RoundedCornerShape(2),
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column() {

            Box(
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {

                EbayPlaceholder(modifier = Modifier.fillMaxSize())


            }
            Column(modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
            ,verticalArrangement = Arrangement.spacedBy(4.dp)) {


//                EbayPlaceholder(modifier = Modifier.fillMaxWidth(0.5f).height(15.dp))
                EbayPlaceholder(modifier = Modifier.fillMaxWidth(0.7f).height(15.dp))
                EbayPlaceholder(modifier = Modifier.fillMaxWidth(0.3f).height(13.dp))

            }

        }
    }
}
