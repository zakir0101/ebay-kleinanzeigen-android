@file:OptIn(ExperimentalFoundationApi::class)

package com.example.ebaykleinanzeigenzakir.window.add

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ebaykleinanzeigenzakir.ImageUrl
import kotlinx.coroutines.launch


@Composable
fun BoxScope.ImagePager(
    pageCount: Int,
    pagerState: PagerState,
    pagerHeight: Int,
    imagesUrl: List<ImageUrl>,

    ) {
    HorizontalPager(pageCount = imagesUrl.size, state = pagerState) {
       Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
           var loaded by remember{ mutableStateOf(false)}

           AsyncImage(
               model = ImageRequest.Builder(LocalContext.current)
                   .data(imagesUrl[it].image_url).crossfade(true).build(),
               contentDescription = null,
               modifier = Modifier
                   .fillMaxSize(),
               contentScale = ContentScale.Crop,
               onSuccess = {loaded = true}
           )
           if (!loaded) {
              Icon(
                  imageVector = Icons.Filled.PhotoCamera,
                  contentDescription = null,
                  modifier = Modifier.size(56.dp),
                  tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
              )
          }
       }
    }

}


@Composable
fun BoxScope.PageNavigatior(pageCount: Int, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    val current = pagerState.currentPage
    IconButton(
        modifier = Modifier.align(Alignment.CenterStart),
        onClick = {
            scope.launch {
                if (current > 0)
                    pagerState.animateScrollToPage(current - 1)
            }
        }) {
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }



    IconButton(
        modifier = Modifier.align(Alignment.CenterEnd),
        onClick = {
            scope.launch {
                if (current < (pageCount - 1))
                    pagerState.animateScrollToPage(current + 1)
            }
        }) {
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoxScope.PageIndecator(pageCount: Int, pagerState: PagerState) {
    Row(
        Modifier
            .height(50.dp)
            .padding(bottom = 6.dp)
            .fillMaxSize()
            .align(Alignment.BottomCenter),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        repeat(pageCount) { iteration ->

            val color =
                if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }.collect {
                }
            }


            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(7.dp)

            )
        }
    }

}

