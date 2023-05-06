@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ebaykleinanzeigenzakir.window.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.ebaykleinanzeigenzakir.EbayViewModel




@Composable
fun SearchWindow(navController: NavHostController, viewModel: EbayViewModel) {

    val searchState by viewModel.searchWindowData.collectAsState()
    LazyColumn(Modifier.fillMaxSize(),
    ){

        if (searchState != null ) {
            items(searchState!!.result) {
                SearchItem(navController, it, viewModel)
            }
        }
        else
            items(15){
                SearchItemPlaceholder()
            }
    }
}



//@Preview
//@Composable
//fun SeeSearchWindow() {
//    EbayKleinanzeigenZakirTheme {
//        SearchWindow(FilterDrawerState, CityDrawerState, CategoryDrawerState)
//    }
//}