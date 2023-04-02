package com.example.ebaykleinanzeigenzakir.window

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ebaykleinanzeigenzakir.EbayViewModel
import com.example.ebaykleinanzeigenzakir.PrimaryButton
import com.example.ebaykleinanzeigenzakir.ui.theme.EbayKleinanzeigenZakirTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep


@Composable
fun YouHaveToConnect(viewModel: EbayViewModel) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = viewModel.internetConencted) {
        scope.launch {
            while (! viewModel.internetConencted) {
                withContext(Dispatchers.IO) {
                    viewModel.isUserLogged()
                    sleep(7000)
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            "Keine Internet Verbindung", color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Composable
fun YouHaveToLogin(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "you have to login", color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
            PrimaryButton(text = "Login", onClick = { navController.navigate("login") })

        }
    }
}


@Preview
@Composable
fun YouHaveToWindow() {

    EbayKleinanzeigenZakirTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            YouHaveToConnect(viewModel())

        }

    }
}

