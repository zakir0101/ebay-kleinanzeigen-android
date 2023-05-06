@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.example.ebaykleinanzeigenzakir.window.add


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.ebaykleinanzeigenzakir.window.Toggler

import com.example.ebaykleinanzeigenzakir.window.search.SearchItem
import kotlinx.coroutines.launch
import java.lang.Float.min

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddWindow(
    navController: NavHostController,
    viewModel: EbayViewModel
) {

    val addState by viewModel.addWindowState.collectAsState()

    if (addState == null)
        AddWindowPlaceholder()
    else
        AddWindowContent(navController, viewModel, addState!!)

}


@Composable
fun AddWindowContent(
    navController: NavHostController,
    viewModel: EbayViewModel,
    addState: AddWindowData
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
        AddWindowTopAppBar(appbarColorAlpha, navController, viewModel)

        Scaffold(
            bottomBar = { BottomButtonBar(  { navController.navigate("sendMessage")} ) },
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
            modifier = Modifier
                .zIndex(0.5f)
                .fillMaxSize()
        ) { padding ->
            val whatever = padding

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
                        val pageCount = addState.images_url.size

                        ImagePager(pageCount, pagerState, pagerHeight, addState.images_url)
                        PageIndecator(pageCount, pagerState)
                        PageNavigatior(pageCount, pagerState)
                    }

                }
                item {
                    AddDetailCard(addState)
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    WhiteCardWithPadding {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            LargeDarkText(text = "Art")
                            SmallDarkText(text = addState.art)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    WhiteCardWithPadding {
                        Text(
                            text = addState.description.trim(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    UserDetailCard(navController, addState.user, viewModel)
                    Spacer(modifier = Modifier.height(10.dp))

                }
                item {
                    WhiteCardWithPadding(modifier = Modifier) {
                        LargeBolderTitleText(text = "Ähnliche Anzeigen")
                    }
                }
                items(items = addState.other_add_similar) {
                    SearchItem(navController = navController, item = it, viewModel = viewModel)
                }


            }

        }
    }


}

@Composable
internal fun BottomButtonBar(onClick: Toggler) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp))
            .padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        OutlinedPrimaryButtonWithIcon(
            "Nachricht",
            icon = Icons.Default.Chat,
            Modifier.weight(1f),
            onClick ={onClick()}
        )
        Spacer(Modifier.width(5.dp))
        PrimaryButtonWithIcon(
            text = "Angebote Machen",
            icon = Icons.Default.EuroSymbol,
            Modifier.weight(1f),
            onClick ={onClick}
        )
    }
}


@Composable
fun UserDetailCard(navController: NavHostController, user: User, viewModel: EbayViewModel) {
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
                    scope.launch {
                        viewModel.activeUserLink = user.user_link
                        viewModel.getUserData()
                        navController.navigate("user")
                    }
                }
                .padding(vertical = 10.dp)

        ) {
            HisInitialCircle(
                text = user.user_name.substring(0 until 1),
                size = "lg",
                modifier = Modifier
            )
            Column(
                Modifier.padding(start = 10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                MediumBoldTitle(text = user.user_name)
                MediumLightText(text = user.user_type)
                MediumLightText(text = user.active_since)
                FollowButton()
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Badge(Modifier, MaterialTheme.colorScheme.primary) {
                        Text(
                            user.user_ad_number.replace("Anzeigen online", "").trim(),
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
        if (user.rating.isNotBlank()) {
            RaitingIconWithText(Icons.Rounded.Mood, user.rating)
        }
        if (user.friendliness.isNotBlank()) {
            Spacer(modifier = Modifier.height(6.dp))
            RaitingIconWithText(Icons.Filled.Chat, user.friendliness)
        }
        if (user.reliability.isNotBlank()) {
            Spacer(modifier = Modifier.height(6.dp))
            RaitingIconWithText(Icons.Filled.ThumbUp, user.reliability)
        }

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
fun AddDetailCard(addState: AddWindowData) {

    val tlMedium = MaterialTheme.typography.titleMedium
    val fSize = tlMedium.fontSize.value * 1.15
    WhiteCardWithPadding() {
        Text(
            text = addState.title,
            style = tlMedium.copy(fontSize = fSize.sp),
            color = MaterialTheme.colorScheme.onSurface
        )
        Row(
            Modifier, horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = " " + addState.price,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            MediumLightText(text = addState.shipping)
        }
        Row(
            Modifier, horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            SmallLightIcon(Icons.Default.LocationOn)
            MediumLightText(text = addState.location)
            SmallLightIcon(icon = Icons.Default.KeyboardArrowRight)
        }
        Row(
            Modifier, horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            SmallLightIcon(Icons.Default.CalendarMonth)
            MediumLightText(text = addState.date)
            Spacer(modifier = Modifier.width(15.dp))
            SmallLightIcon(icon = Icons.Default.Visibility)
            MediumLightText(text = addState.views)
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