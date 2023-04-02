@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.ebaykleinanzeigenzakir

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ebaykleinanzeigenzakir.ui.theme.EbayKleinanzeigenZakirTheme
import com.example.ebaykleinanzeigenzakir.window.Application
import com.example.ebaykleinanzeigenzakir.window.search.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationWithTheme()
        }
    }
}

@Composable
fun ApplicationWithTheme(viewModel:EbayViewModel = viewModel(factory=EbayViewModel.Factory)) {
    EbayKleinanzeigenZakirTheme(darkTheme = viewModel.darkMode, dynamicColor = viewModel.dynamicColor) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Application(viewModel)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ApplicationWithTheme()
}