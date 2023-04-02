package com.example.ebaykleinanzeigenzakir.window.search

import androidx.compose.foundation.layout.*
import androidx.compose.material.*

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ebaykleinanzeigenzakir.FilterHeader
import com.example.ebaykleinanzeigenzakir.ui.theme.EbayKleinanzeigenZakirTheme
import com.example.ebaykleinanzeigenzakir.window.toggle
import kotlinx.coroutines.launch


@Composable
fun PriceDrawer(
    priceDrawerState: DrawerState,
    rightDrawerBgColor: Color,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    RightModalDrawer(
        drawerState = priceDrawerState,
        drawerBackgroundColor = rightDrawerBgColor,
        content = content,
        drawerContent = {
            FilterHeader {
                priceDrawerState.toggle(scope)
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 25.dp)) {
                PriceTextField(label = "Ab")
                Spacer(modifier = Modifier.width(30.dp))
                PriceTextField(label = "Bis")
            }

        }
        )

}

@Composable
fun RowScope.PriceTextField(label: String) {
    var text  by remember{ mutableStateOf("")}
    Box(modifier = Modifier.weight(1f)) {
        OutlinedTextField(value = text, onValueChange = { text = it },
            label = { Text(text = label, Modifier,) } ,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground

            ))
    }
}


@Preview
@Composable
fun ShowPriceDrawer() {
    EbayKleinanzeigenZakirTheme(darkTheme = true) {
        val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Open)
        val scope = rememberCoroutineScope()

        val toggleDrawer: () -> Unit = {
            scope.launch {
                drawerState.apply {
                    if (isClosed) open() else close()
                }
            }
        }
        val rightDrawerBgColor =MaterialTheme.colorScheme.surfaceColorAtElevation(15.dp)

        PriceDrawer(drawerState, rightDrawerBgColor) {
            Column {
                Text("Text in City Drawer")
                Button(onClick = {
                    toggleDrawer()
                }) {
                    Text("Click to open")
                }
            }
        }
    }
}


