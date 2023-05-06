package com.example.ebaykleinanzeigenzakir.window.myadd

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.ebaykleinanzeigenzakir.EbayViewModel
import com.example.ebaykleinanzeigenzakir.window.user.UserWindow

@Composable
fun MyAddWindow(navController: NavHostController, viewModel: EbayViewModel) {
    UserWindow(navController = navController, viewModel = viewModel)
}