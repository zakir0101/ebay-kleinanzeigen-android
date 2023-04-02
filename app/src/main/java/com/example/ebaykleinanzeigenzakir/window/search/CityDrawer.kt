@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ebaykleinanzeigenzakir.window.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.DrawerState
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import com.example.ebaykleinanzeigenzakir.FilterHeader
import com.example.ebaykleinanzeigenzakir.ui.theme.EbayKleinanzeigenZakirTheme
import com.example.ebaykleinanzeigenzakir.window.toggle


@Composable
fun CityDrawer(
    CityDrawerState: DrawerState,
    rightDrawerBgColor: Color,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    RightModalDrawer(
        drawerState = CityDrawerState,
        drawerBackgroundColor = rightDrawerBgColor,
        content = content,
        drawerContent = {
            FilterHeader { CityDrawerState.toggle(scope) }
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp, 15.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                var text by remember { mutableStateOf("") }
                val setValue: (String) -> Unit = { text = it }
                val all = listOf("aaa", "baa", "aab", "abb", "bab")

                FilterCard {
                    TextFieldWithDropdown(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 0.dp,end = 0.dp, bottom =  5.dp),
                        value = text,
                        setValue = setValue,
                        list = all,
                        placeholder = "Suche"
                    )
                }

                FilterCard {
                    var range by remember { mutableStateOf(0f) }
                    FilterTextHead(text = "Umkreis: ${range.toInt()} km", true)
                    Slider(
                        value = range,
                        onValueChange = { range = it },
                        Modifier.padding(34.dp, 0.dp),
                        valueRange = 0f.rangeTo(200f),
                        steps = 40,
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary,
                            activeTickColor = MaterialTheme.colorScheme.primary,
                            inactiveTrackColor = MaterialTheme.colorScheme.background,
                            inactiveTickColor = MaterialTheme.colorScheme.background
                        )
                    )

                }

            }


        },

        )
}


@Composable
fun TextFieldWithDropdown(
    modifier: Modifier = Modifier,
    value: String,
    setValue: (String) -> Unit,
    list: List<String>,
    placeholder: String = "",
    onDismissRequest: (() -> Unit)? = null,
) {
    var expanded by remember { mutableStateOf(true) }
    val onDismissRequest2 = onDismissRequest ?: { expanded = false }

    var text by rememberSaveable { mutableStateOf("") }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }


    Box(
        modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)) {

        androidx.compose.material3.TextField(
            value = value,

            onValueChange = {
                if (it.length > 1)
                    expanded = true
                    setValue(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused)
                        onDismissRequest2()
                }
                .onGloballyPositioned { cord ->
                    textfieldSize = cord.size.toSize()
                }
                .padding(0.dp).scale(0.8f,0.8f),
            placeholder = { Text(placeholder,color=MaterialTheme.colorScheme.onBackground) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground),

            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear, contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

        )


        DropdownMenu(
            expanded = expanded,
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = onDismissRequest2,
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                .background(MaterialTheme.colorScheme.surface)

        ) {
            list.forEach { label ->
                androidx.compose.material.DropdownMenuItem(
                    onClick = {
                        setValue(label)
                        expanded = false
                    }) {
                    Text(
                        modifier = Modifier,
                        text = label, color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ShowCityDrawer() {
    EbayKleinanzeigenZakirTheme(darkTheme = false) {
        val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Open)
        val scope = rememberCoroutineScope()
        val rightDrawerBgColor =MaterialTheme.colorScheme.surfaceColorAtElevation(15.dp)
        CityDrawer(drawerState, rightDrawerBgColor) {
            Column {
                Text("Text in Bodycontext")
                Button(onClick = {
                    drawerState.toggle(scope)
                }) {
                    Text("Click to open")
                }
            }
        }
    }
}