@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.ebaykleinanzeigenzakir.window.login

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ebaykleinanzeigenzakir.EbayViewModel
import com.example.ebaykleinanzeigenzakir.PrimaryButton
import com.example.ebaykleinanzeigenzakir.ui.theme.EbayKleinanzeigenZakirTheme
import com.example.ebaykleinanzeigenzakir.window.publish.getTextFieldColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginWindow(
    navController: NavController,
    viewModel: EbayViewModel = viewModel(factory = EbayViewModel.Factory)
) {
//    val scope = rememberCoroutineScope()


    if (":)" in viewModel.parsingStatus) {
        navController.navigate("main")
    }


    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            OutlinedTextField(
                value = viewModel.cookieText,
                onValueChange = { s: String -> viewModel.cookieText = s; },
                colors = getTextFieldColors(),
                placeholder = { Text("Copy paste Cookie here") },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide();focusManager.clearFocus() })
            )
        }


        Row(
            Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val textColor = if ("not" in viewModel.parsingStatus)
                Color.Red else MaterialTheme.colorScheme.primary
            Text(viewModel.parsingStatus, color = textColor)
            Spacer(modifier = Modifier.weight(1f))
            PrimaryButton(text = "Login", onClick = { viewModel.onCookieSubmit() })
        }


    }
}


@Preview
@Composable
fun ShowLoginWindow() {
    val navController = rememberNavController()

    EbayKleinanzeigenZakirTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LoginWindow(navController)
        }

    }
}