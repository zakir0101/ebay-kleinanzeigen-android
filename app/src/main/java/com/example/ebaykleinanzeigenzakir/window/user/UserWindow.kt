package com.example.ebaykleinanzeigenzakir.window.user

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ebaykleinanzeigenzakir.*
import com.example.ebaykleinanzeigenzakir.window.add.*
import com.example.ebaykleinanzeigenzakir.window.search.SearchItem

class RatingItem(val name1: String, val name2: String, val icon: ImageVector)

fun getRatingData(user: User): List<RatingItem> {
    return listOf(
        RatingItem(user.rating.replace("Zufriedenheit", ""), "ZufriedenHeit", Icons.Filled.Mood),
        RatingItem(
            user.friendliness.replace("freundlich", ""),
            "freundlich",
            Icons.Filled.ChatBubble
        ),
        RatingItem(
            user.reliability.replace("zuverlässig", ""),
            "zuverlässig",
            Icons.Filled.ThumbUp
        ),
        RatingItem(user.reply_rate.replace("Antwortrate", ""), "Antwortrate", Icons.Filled.Chat),
        RatingItem(user.reply_speed.replace("Antwortzeit", ""), "Antwortzeit", Icons.Filled.Timer),
        RatingItem(user.followers.replace("Follower", ""), "Follower", Icons.Filled.Person),
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserWindow(navController: NavHostController, viewModel: EbayViewModel) {
    val userState by viewModel.userWindowState.collectAsState()

    if (userState == null)
        userWindowPlaceholder()
    else
        UserWindowContent(navController, viewModel, userState!!)
}

@Composable
fun UserWindowContent(
    navController: NavHostController,
    viewModel: EbayViewModel,
    userState: UserWindowData
) {

    LazyColumn(
        Modifier
            .fillMaxSize(),

        ) {
        item {
            UserCard(userState.user)
            Spacer(modifier = Modifier.height(10.dp))
        }
        items(userState.user_adds) {
            SearchItem(navController = navController, item = it, viewModel = viewModel)
        }

    }
}


@Composable
fun UserCard(user: User) {
    WhiteCardWithPadding() {

        Column(
            Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HisInitialCircle(
                text = user.user_name.substring(0 until 1),
                size = "lg"
            )
            MediumBoldTitle(text = user.user_name)
            MediumLightText(text = user.user_type + " - " + user.active_since)
            FollowButton()
            Row(Modifier.horizontalScroll(rememberScrollState())) {
                for (it in getRatingData(user)) {
                    if(it.name1.isNotBlank()) {
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

//
//@Preview
//@Composable
//fun ShowAddWindow() {
//    val navController = rememberNavController()
//    EbayKleinanzeigenZakirTheme() {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            UserWindow(navController, viewModel)
//
//        }
//
//    }
//}