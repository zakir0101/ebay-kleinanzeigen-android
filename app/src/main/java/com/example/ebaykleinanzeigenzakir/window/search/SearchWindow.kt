@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ebaykleinanzeigenzakir.window.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController


@Composable
fun SearchWindow(navController: NavHostController) {


    LazyColumn(Modifier.fillMaxSize(),
    ){
        items(20){
            SearchItem(navController)
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