package com.example.ebaykleinanzeigenzakir.window.add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Mood
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.ebaykleinanzeigenzakir.*
import com.example.ebaykleinanzeigenzakir.window.search.SearchItemPlaceholder
import java.lang.Float

@Composable
fun AddWindowPlaceholder() {
    var appbarColorAlpha by remember { mutableStateOf(1f) }
    val pagerHeight = 250
    val lazyColumnState = rememberLazyListState()
    val firstElementIndex by remember { derivedStateOf { lazyColumnState.firstVisibleItemIndex } }
    val firstElementOffset by remember { derivedStateOf { lazyColumnState.firstVisibleItemScrollOffset } }

    if (firstElementIndex == 0) {
        appbarColorAlpha = (firstElementOffset.toFloat() / pagerHeight.toFloat())
        appbarColorAlpha = Float.min(1f, appbarColorAlpha)
    }

    Box(modifier = Modifier.fillMaxSize()) {
//        AddWindowTopAppBarP(appbarColorAlpha, navController,viewModel)

        Scaffold(
            bottomBar = { BottomButtonBar({  }) },
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
                        EbayPlaceholder(modifier = Modifier.fillMaxSize())
                    }

                }
                item {
                    AddDetailCardPlaceholder()
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    WhiteCardWithPadding {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            LargeDarkText(text = "Art")
                            EbayPlaceholder(
                                modifier = Modifier
                                    .fillMaxWidth(0.26f)
                                    .height(10.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    WhiteCardWithPadding {
                        EbayPlaceholder(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(10.dp)
                        )
                        EbayPlaceholder(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(10.dp)
                        )
                        EbayPlaceholder(
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .height(10.dp)
                        )
                        EbayPlaceholder(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(10.dp)
                        )
                        EbayPlaceholder(
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    UserDetailCardPlaceholder()
                    Spacer(modifier = Modifier.height(10.dp))

                }
                item {
                    WhiteCardWithPadding(modifier = Modifier) {
                        LargeBolderTitleText(text = "Ähnliche Anzeigen")
                    }
                }
                items(10) {
                    SearchItemPlaceholder()
                }


            }

        }
    }

}

@Composable
fun UserDetailCardPlaceholder() {
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
                .padding(vertical = 10.dp)

        ) {
            HisInitialCircle(text = "?", size = "lg", modifier = Modifier)
            Column(
                Modifier.padding(start = 10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                EbayPlaceholder(
                    modifier = Modifier
                        .fillMaxWidth(0.35f)
                        .height(12.dp)
                )

                EbayPlaceholder(
                    modifier = Modifier
                        .fillMaxWidth(0.35f)
                        .height(12.dp)
                )

                EbayPlaceholder(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(12.dp)
                )

//                FollowButton()
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SmallLightIcon(icon = Icons.Default.KeyboardArrowRight)

                }
            }

        }

        Divider(
            Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        )

        RatingIconWithTextPlaceholder(Icons.Rounded.Mood,
            Modifier
                .fillMaxWidth(0.5f)
                .height(13.dp))
        Spacer(modifier = Modifier.height(6.dp))
        RatingIconWithTextPlaceholder(Icons.Filled.Chat,
            Modifier
                .fillMaxWidth(0.5f)
                .height(13.dp))

        Spacer(modifier = Modifier.height(6.dp))
        RatingIconWithTextPlaceholder(Icons.Filled.ThumbUp,
            Modifier
                .fillMaxWidth(0.5f)
                .height(13.dp))

    }

}

@Composable
fun RatingIconWithTextPlaceholder(mood: ImageVector, modifier: Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = mood,
            contentDescription = null,
            Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.tertiary
        )
        EbayPlaceholder(modifier = modifier)
    }
}

@Composable
fun AddDetailCardPlaceholder() {

    val tlMedium = MaterialTheme.typography.titleMedium
    val fSize = tlMedium.fontSize.value * 1.15
    WhiteCardWithPadding() {
        EbayPlaceholder(
            modifier = Modifier
                .fillMaxWidth(0.86f)
                .height(14.dp)
        )
        Row(
            Modifier, horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            EbayPlaceholder(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth(0.2f)
            )
            Spacer(modifier = Modifier.width(5.dp))
            EbayPlaceholder(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth(0.4f)
            )
        }
        Row(
            Modifier, horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            SmallLightIcon(Icons.Default.LocationOn)
            EbayPlaceholder(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(10.dp)
            )
            SmallLightIcon(icon = Icons.Default.KeyboardArrowRight)
        }
        Row(
            Modifier, horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            SmallLightIcon(Icons.Default.CalendarMonth)
            EbayPlaceholder(
                modifier = Modifier
                    .fillMaxWidth(0.36f)
                    .height(10.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            SmallLightIcon(icon = Icons.Default.Visibility)
            EbayPlaceholder(
                modifier = Modifier
                    .fillMaxWidth(0.36f)
                    .height(10.dp)
            )
        }
    }

}
