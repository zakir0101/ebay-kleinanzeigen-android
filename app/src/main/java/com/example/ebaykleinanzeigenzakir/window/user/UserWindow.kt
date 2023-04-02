package com.example.ebaykleinanzeigenzakir.window.user

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ebaykleinanzeigenzakir.*
import com.example.ebaykleinanzeigenzakir.ui.theme.EbayKleinanzeigenZakirTheme
import com.example.ebaykleinanzeigenzakir.window.add.*
import com.example.ebaykleinanzeigenzakir.window.search.SearchItem

class RatinigItem(val name1: String, val name2: String, val icon: ImageVector)

fun getRatingData(): List<RatinigItem> {
    return listOf(
        RatinigItem("Top", "ZufriedenHeit", Icons.Filled.Mood),
        RatinigItem("Besonders", "freundlich", Icons.Filled.ChatBubble),
        RatinigItem("Besonders", "Zuverlässig", Icons.Filled.ThumbUp),
        RatinigItem("99%", "Antwortrate", Icons.Filled.Chat),
        RatinigItem("3h", "Antwortzeit", Icons.Filled.Timer),
        RatinigItem("3", "Follower", Icons.Filled.Person),
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserWindow(navController: NavHostController) {

    LazyColumn(
        Modifier
            .fillMaxSize(),

        ) {
        item {
            UserCard()
            Spacer(modifier = Modifier.height(10.dp))
        }
        items(15){
            SearchItem(navController = navController)
        }
    }

}

@Composable
fun UserCard() {
    WhiteCardWithPadding() {

        Column(
            Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HisInitialCircle(text = "NÜ", size = "lg")
            MediumBoldTitle(text = "Nesrin Ünal")
            MediumLightText(text = "Private Anbieter - Aktiv seit 28.03.20")
            FollowButton()
            Row(Modifier.horizontalScroll(rememberScrollState())) {
                for (it in getRatingData()) {


                    Column(
                        Modifier.width(80.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RatingIcon(icon = it.icon, size = 60)
                        Spacer(modifier = Modifier.height(10.dp))
                        SmallLightText(text = it.name1)
                        SmallLightLabel(text = it.name2)
                    }
//                   Spacer(modifier = Modifier.width(10.dp))

                }
            }
        }
    }
}


@Composable
fun RatingIcon(icon: ImageVector, size: Int = 55) {
    Box(
        modifier = Modifier
            .border(
                2.dp,
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(50)
            )
            .size(size.dp)
            .padding(12.dp)


    ) {
        Icon(
            contentDescription = null,
            modifier = Modifier
                .size((size - 12).dp),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            imageVector = icon

        )
    }

}


@Preview
@Composable
fun ShowAddWindow() {
    val navController = rememberNavController()
    EbayKleinanzeigenZakirTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            UserWindow(navController)

        }

    }
}