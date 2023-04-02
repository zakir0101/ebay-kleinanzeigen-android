@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.example.ebaykleinanzeigenzakir.window.add


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Mood
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ebaykleinanzeigenzakir.*
import com.example.ebaykleinanzeigenzakir.ui.theme.EbayKleinanzeigenZakirTheme

import com.example.ebaykleinanzeigenzakir.window.search.SearchItem
import kotlinx.coroutines.launch
import java.lang.Float.min

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddWindow(
    navController: NavHostController,
    viewModel: EbayViewModel
) {


    var appbarColorAlpha by remember { mutableStateOf(1f) }
    val pagerHeight = 250
    val lazyColumnState = rememberLazyListState()
    val firstElementIndex by remember { derivedStateOf { lazyColumnState.firstVisibleItemIndex } }
    val firstElementOffset by remember { derivedStateOf { lazyColumnState.firstVisibleItemScrollOffset } }

    if (firstElementIndex == 0) {
        appbarColorAlpha = (firstElementOffset.toFloat() / pagerHeight.toFloat())
        appbarColorAlpha = min(1f, appbarColorAlpha)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AddWindowTopAppBar(appbarColorAlpha, navController,viewModel.switchDarkMode,viewModel.darkMode)

        Scaffold(
            bottomBar = { BottomButtonBar() },
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
            modifier = Modifier
                .zIndex(0.5f)
                .fillMaxSize()
        ) {
            val whatever = it

            LazyColumn(
                Modifier
                    .fillMaxSize(),
                state = lazyColumnState
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(pagerHeight.dp)
                    ) {
                        val pagerState = rememberPagerState()
                        val pageCount = 4

                        ImagePager(pageCount, pagerState, pagerHeight)
                        PageIndecator(pageCount, pagerState)
                        PageNavigatior(pageCount, pagerState)
                    }

                }
                item {
                    AddDetailCard()
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    WhiteCardWithPadding {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            LargeDarkText(text = "Art")
                            SmallDarkText(text = "Samsung Tablet")
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    WhiteCardWithPadding {
                        Text(
                            text = "ich verkaufe dieses Buch cover von Samsung\nOriginal\nFür Galaxy Tab 23456\nMit zwei Standfunktionen\n\nETF-3355\n\nNeu und ungeöffnet",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    UserDetailCard(navController)
                    Spacer(modifier = Modifier.height(10.dp))

                }
                item {
                    WhiteCardWithPadding(modifier = Modifier) {
                        LargeBolderTitleText(text = "Ähnliche Anzeigen")
                    }
                }
                items(6) {
                    SearchItem(navController = navController)
                }

            }

        }
    }
}

@Composable
fun BottomButtonBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp))
            .padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        OutlinedPrimaryButtonWithIcon("Nachricht", icon = Icons.Default.Chat, Modifier.weight(1f))
        Spacer(Modifier.width(5.dp))
        PrimaryButtonWithIcon(
            text = "Angebote Machen",
            icon = Icons.Default.EuroSymbol,
            Modifier.weight(1f)
        )
    }
}


@Composable
fun UserDetailCard(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    WhiteCardWithPadding() {
        LargeBolderTitleText("Anbieter")
        Divider(
            Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        )

        Row(
            Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clickable {
                    scope.launch { navController.navigate("user") }
                }
                .padding(vertical = 10.dp)

        ) {
            HisInitialCircle(text = "NL", size = "lg", modifier = Modifier)
            Column(
                Modifier.padding(start = 10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                MediumBoldTitle(text = "Nesrin Ünal")
                MediumLightText(text = "Private Anbeiter")
                MediumLightText(text = "Aktiv seit 28.03.2020")
                FollowButton()
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Badge(Modifier, MaterialTheme.colorScheme.primary) {
                        Text(
                            "37",
                            Modifier,
                            MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    SmallLightIcon(icon = Icons.Default.KeyboardArrowRight)

                }
            }

        }

        Divider(
            Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        )

        RaitingIconWithText(Icons.Rounded.Mood, "Zufriedenheit: TOP")
        Spacer(modifier = Modifier.height(6.dp))
        RaitingIconWithText(Icons.Filled.Chat, "Besonders froundlich")
        Spacer(modifier = Modifier.height(6.dp))
        RaitingIconWithText(Icons.Filled.ThumbUp, "Besonders zuverlässig")


    }


}


@Composable
fun RaitingIconWithText(icon: ImageVector, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.tertiary
        )
        LargeDarkText(text = label)
    }
}

@Composable
fun FollowButton() {
    OutlinedPrimaryButtonWithIcon("Folgen", Icons.Default.PersonAdd)
}

@Composable
fun AddDetailCard() {

    val tlMedium = MaterialTheme.typography.titleMedium
    val fSize = tlMedium.fontSize.value * 1.15
    WhiteCardWithPadding() {
        Text(
            text = "Original Samsung Book Cover Ef-BT510 für Galaxy Tab A 2019 10,1",
            style = tlMedium.copy(fontSize = fSize.sp),
            color = MaterialTheme.colorScheme.onSurface
        )
        Row(
            Modifier, horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = " " + "8 $ VB",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            MediumLightText(text = "Versand möglich")
        }
        Row(
            Modifier, horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            SmallLightIcon(Icons.Default.LocationOn)
            MediumLightText(text = "23453 well der Stadt")
            SmallLightIcon(icon = Icons.Default.KeyboardArrowRight)
        }
        Row(
            Modifier, horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            SmallLightIcon(Icons.Default.CalendarMonth)
            MediumLightText(text = "Heute, 22:14")
            Spacer(modifier = Modifier.width(15.dp))
            SmallLightIcon(icon = Icons.Default.Visibility)
            MediumLightText(text = "0")
        }
    }
}


@Preview
@Composable
fun ShowAddWindow() {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    EbayKleinanzeigenZakirTheme() {
        androidx.compose.material.Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AddWindow(navController, viewModel(factory = EbayViewModel.Factory))

        }

    }
}