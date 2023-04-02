@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ebaykleinanzeigenzakir

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun MainWindow(navController: NavHostController, viewModel: EbayViewModel ) {
    val mainState by viewModel.mainState.collectAsState()
//    viewModel.getMain()
    LazyVerticalGrid(

        columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(20.dp,20.dp,20.dp,20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),

    )
    {

        item(span = { GridItemSpan(2) }) {
            Text(
                "Galarie",
                Modifier.padding(0.dp),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        if(mainState  != null) {
            item(span = { GridItemSpan(2) }) {
                GalleryList(navController, mainState!!.galerie)
            }
        }
        item(span = { GridItemSpan(2) })  {
            Text(
                "Fur dich empfohlen",
                Modifier
                    .padding(0.dp),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        if(mainState  != null) {
            items(mainState!!.main) {
                MainItem(navController, it)
            }
        }
    }
}





@Composable
fun GalleryList(navController: NavHostController, galleryList: List<MainItemData>?) {
    LazyRow(
        contentPadding = PaddingValues(0.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)

    ) {
        items(galleryList!!) {
            MainItem(navController, it)
        }

    }

}
//
//@Composable
//fun MainList() {
////    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
//    LazyVerticalGrid(
//
//        columns = GridCells.Adaptive(minSize = 128.dp),
//        contentPadding = PaddingValues(20.dp,0.dp,20.dp,20.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp),
//        horizontalArrangement = Arrangement.spacedBy(16.dp),
//        userScrollEnabled = true,
//        modifier = Modifier.height(screenHeight)
//    ) {
//
//        items(20) {
//            MainItem()
//        }
//    }
//
//}




