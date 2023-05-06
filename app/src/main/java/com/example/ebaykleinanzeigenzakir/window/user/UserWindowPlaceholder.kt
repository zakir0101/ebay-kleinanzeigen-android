package com.example.ebaykleinanzeigenzakir.window.user

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ebaykleinanzeigenzakir.*
import com.example.ebaykleinanzeigenzakir.window.add.FollowButton
import com.example.ebaykleinanzeigenzakir.window.search.SearchItemPlaceholder

@Composable
fun userWindowPlaceholder() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),

        ) {
        item {
            UserCardPlaceholder()
            Spacer(modifier = Modifier.height(10.dp))
        }
        items(10) {
            SearchItemPlaceholder()
        }

    }

}



fun getRatingPlaceholder(): List<RatingItem> {
    return listOf(
        RatingItem("", "ZufriedenHeit", Icons.Filled.Mood),
        RatingItem(
            "",
            "freundlich",
            Icons.Filled.ChatBubble
        ),
        RatingItem("",
            "zuverlässig",
            Icons.Filled.ThumbUp
        ),
        RatingItem("", "Antwortrate", Icons.Filled.Chat),
        RatingItem("", "Antwortzeit", Icons.Filled.Timer),
        RatingItem("", "Follower", Icons.Filled.Person),
    )
}

@Composable
fun UserCardPlaceholder () {
    WhiteCardWithPadding() {
        Column(
            Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HisInitialCircle(
                text = "?",
                size = "lg"
            )
            EbayPlaceholder(modifier = Modifier
                .height(12.dp)
                .fillMaxWidth(0.2f))
            EbayPlaceholder(modifier = Modifier
                .height(12.dp)
                .fillMaxWidth(0.7f))
            FollowButton()
            Row(Modifier.horizontalScroll(rememberScrollState())) {
                for (it in getRatingPlaceholder()) {
                    if(it.name1.isNotBlank()) {
                        Column(
                            Modifier.width(80.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            RatingIcon(icon = it.icon, size = 60)
                            Spacer(modifier = Modifier.height(10.dp))
                            EbayPlaceholder(modifier = Modifier
                                .height(12.dp)
                                .fillMaxWidth(0.5f))
                            Spacer(modifier = Modifier.height(4.dp))
                            EbayPlaceholder(modifier = Modifier
                                .height(12.dp)
                                .fillMaxWidth(0.5f))
                        }

                    }
                }
            }
        }
    }

}
