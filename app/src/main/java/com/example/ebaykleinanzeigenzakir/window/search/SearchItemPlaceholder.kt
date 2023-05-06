package com.example.ebaykleinanzeigenzakir.window.search

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ebaykleinanzeigenzakir.EbayPlaceholder


@Composable
fun SearchItemPlaceholder() {


    Row(
        Modifier
            .height(120.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)

    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(162.dp)
                .padding(0.dp, 1.dp), contentAlignment = Alignment.Center
        ) {
            EbayPlaceholder(modifier = Modifier.fillMaxSize())
        }
        Column(
            Modifier
                .fillMaxHeight()
                .weight(1f)
                .border(1.dp, color = MaterialTheme.colorScheme.background)
                .padding(start = 10.dp, top = 5.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            EbayPlaceholder(modifier = Modifier
                .height(10.dp)
                .fillMaxWidth(0.25f))
            Spacer(modifier = Modifier.height(4.dp))
            EbayPlaceholder(modifier = Modifier
                .fillMaxWidth()
                .height(15.dp))
            EbayPlaceholder(modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(15.dp))

            EbayPlaceholder(modifier = Modifier.fillMaxWidth(0.2f).height(13.dp))
            Spacer(modifier = Modifier.weight(1f))



        }
    }

}
