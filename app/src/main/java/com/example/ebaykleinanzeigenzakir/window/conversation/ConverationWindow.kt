@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ebaykleinanzeigenzakir.window.conversation


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items


import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ebaykleinanzeigenzakir.Conversation
import com.example.ebaykleinanzeigenzakir.ConversationImage
import com.example.ebaykleinanzeigenzakir.EbayViewModel

@Composable
internal fun ConversationWindow(navController: NavHostController, viewModel: EbayViewModel) {
    val conversationState by viewModel.conversationWindowState.collectAsState()


    LazyColumn() {

        if (conversationState != null) {
            items(conversationState!!.conversations) {
                ConversationItem(navController, it, viewModel)
            }
        } else {
            items(14) {
                ConversationItemPlaceholder()
            }
        }
    }
}


@Composable
fun ConversationItem(
    navController: NavHostController,
    conversation: Conversation,
    viewModel: EbayViewModel
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable {
                viewModel.activeConversationId = conversation.id
                viewModel.getMessages()
                navController.navigate("message")
            }
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.background)
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ConversationImage(conversation = conversation)
        ConversationItemText(conversation, viewModel)
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.ConversationItemText(conversation: Conversation, viewModel: EbayViewModel) {

    val commonTextStyle = MaterialTheme.typography.bodySmall
    val commonTextColor = MaterialTheme.colorScheme.onBackground

    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(start = 20.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = viewModel.getHisName(conversation),
                style = commonTextStyle,
                color = commonTextColor
            )
            Text(text = "Fr. 17.03.2023", style = commonTextStyle, color = commonTextColor)
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = conversation.adTitle,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
            if (conversation.unreadMessagesCount > 0) {
                Badge(
                    modifier = Modifier.padding(top = 4.dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ) {

                    Text(
                        text = conversation.unreadMessagesCount.toString(),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

        }
        Text(
            text = conversation.textShortTrimmed.replace("\n", ""),
            style = commonTextStyle,
            color = commonTextColor
        )

    }

}


//
//@Preview
//@Composable
//fun ShowConversationWindow() {
//    val navController = rememberNavController()
//    val setCurrent: (String) -> Unit = { s: String -> { var a = s } }
//    EbayKleinanzeigenZakirTheme() {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            ConversationWindow(navController, viewModel)
//
//        }
//
//    }
//}