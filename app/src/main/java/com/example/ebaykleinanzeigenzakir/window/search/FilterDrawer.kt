@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.ebaykleinanzeigenzakir.window.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape


import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.*
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.filled.Info


import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.ebaykleinanzeigenzakir.EbayDropDown
import com.example.ebaykleinanzeigenzakir.EbaySwitch
import com.example.ebaykleinanzeigenzakir.FilterHeader
import com.example.ebaykleinanzeigenzakir.ui.theme.EbayKleinanzeigenZakirTheme
import com.example.ebaykleinanzeigenzakir.window.toggle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FilterDrawer(
    filterDrawerState: DrawerState,
    cityDrawerState: DrawerState,
    categoryDrawerState: DrawerState,
    priceDrawerState: DrawerState,
    rightDrawerBgColor: Color,
    content: @Composable () -> Unit
) {

    val scope = rememberCoroutineScope()
    RightModalDrawer(
        drawerState = filterDrawerState,
        drawerBackgroundColor = rightDrawerBgColor,
        content = content,

        drawerContent = {
            FilterHeader { filterDrawerState.toggle(scope) }
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp, 15.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                FilterCard {
                    FilterTextHead(text = "Ort")
                    FilterTextWithArrow(text = "Ganz Deutschland",
                        Modifier.clickable { cityDrawerState.toggle(scope) })
                    Divider(Modifier, color = MaterialTheme.colorScheme.onBackground, thickness = 0.5.dp)

                    FilterTextHead(text = "Preis")
                    FilterTextWithArrow(text = "Bliebig",
                        Modifier.clickable { priceDrawerState.toggle(scope) })
                    Divider(Modifier, color = MaterialTheme.colorScheme.onBackground, thickness = 0.5.dp)

                    FilterTextHead(text = "Kategorie")
                    FilterTextWithArrow(text = "Alle Kategorien",
                        Modifier.clickable { categoryDrawerState.toggle(scope) })
                }


                FilterCard {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        var checked by remember { mutableStateOf(false) }
                        val setChecked = {checked = !checked}
                        FilterTextHead("Nur \"Direkt Kaufen\"", true)
                        EbaySwitch(checked,setChecked)
                    }

                }


                FilterCard {

                    val suggestions = listOf("Alle", "DHL", "Hermes")
                    FilterTextHead("Paketdienst")
                    EbayDropDown(suggestions)
                }


                FilterCard {

                    val suggestions1 = listOf("Private & Gewerblich", "Privat", "Gewerblich")
                    val suggestions2 = listOf("Angebote & Gesuche", "Angebote", "Gesuche")


                    FilterTextHead("Verkäufer")
                    EbayDropDown(suggestions1)
                    Divider(Modifier,color = MaterialTheme.colorScheme.onBackground, thickness = 0.5.dp)
                    FilterTextHead("Angebotestype")
                    EbayDropDown(suggestions2)


                }

                FilterCard {
                    val suggestions = listOf("Neuste zuesrt", "Günstigte zuerste")
                    FilterTextHead("Sortierung")
                    EbayDropDown(suggestions)

                }


                Row(
                    Modifier.padding(start = 10.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info, contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    FilterTextHead(text = "Möchtest du mehr zu Sortierung Erfahren", true)

                }


            }


        }
    )
}



@Composable
fun FilterCard(content: @Composable () -> Unit) {
    Card(
        Modifier,
        shape = RoundedCornerShape(0),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            Modifier.padding(0.dp, 10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            content()
        }
    }

}


@Composable
fun FilterTextWithArrow(text: String, modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(end = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FilterText(text)
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            tint = MaterialTheme.colorScheme.onBackground, contentDescription = null
        )
    }
}


@Composable
fun FilterTextHead(text: String, noPaddingBottom: Boolean = false) {

    var pdBottom = if (noPaddingBottom) 0.dp else 5.dp

    Text(
        text = text, color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier.padding(start = 10.dp, bottom = pdBottom)
    )
}


@Composable
fun FilterText(text: String, noPaddingBottom: Boolean = false, modifier: Modifier = Modifier) {
    var pdBottom = if (noPaddingBottom) 0.dp else 5.dp

    Text(
        text = text, color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.labelLarge,
        modifier = modifier.padding(start = 10.dp, bottom = pdBottom, end = 10.dp)
    )
}


@Preview
@Composable
fun ShowFilterDrawer() {
    EbayKleinanzeigenZakirTheme(darkTheme = false) {
        val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Open)
        val drawerState2: DrawerState = rememberDrawerState(initialValue = DrawerValue.Open)
        val drawerState3: DrawerState = rememberDrawerState(initialValue = DrawerValue.Open)
        val drawerState4: DrawerState = rememberDrawerState(initialValue = DrawerValue.Open)
        val scope = rememberCoroutineScope()
        val rightDrawerBgColor =MaterialTheme.colorScheme.surfaceColorAtElevation(15.dp)


        FilterDrawer(drawerState, drawerState2, drawerState3, drawerState4, rightDrawerBgColor) {
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