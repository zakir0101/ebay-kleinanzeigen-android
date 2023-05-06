package com.example.ebaykleinanzeigenzakir.window.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ebaykleinanzeigenzakir.Conversation
import com.example.ebaykleinanzeigenzakir.EbayViewModel
import com.example.ebaykleinanzeigenzakir.HisInitialCircle
import com.example.ebaykleinanzeigenzakir.Message
import kotlinx.coroutines.launch

@Composable
fun MessageWindow(navController: NavHostController, viewModel: EbayViewModel) {
    val activeConversation by viewModel.activeConversation.collectAsState()

    if(activeConversation == null)
        MessageWindowPlaceholder()
    else
        MessageWindowContent(navController,viewModel,activeConversation!!)

}

@Composable
fun MessageWindowContent(
    navController: NavHostController,
    viewModel: EbayViewModel,
    activeConversation: Conversation
) {

    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    Column(Modifier.fillMaxSize()) {
        val messages = activeConversation.messages
        LaunchedEffect(messages.size) {
            scope.launch {
                scrollState.animateScrollToItem(messages.size - 1)
            }
        }
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(),
            verticalArrangement = Arrangement.Bottom
        ) {
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            itemsIndexed(items = messages) { index, it ->
                if (it.boundness=="OUTBOUND")
                    MyMessage(it)
                else
                    HisMessage(navController,it,viewModel,activeConversation)

                Spacer(modifier = Modifier.height(15.dp))
                if (index == (messages.size - 1))
                    Spacer(modifier = Modifier.height(15.dp))
            }
        }


        SendButton(viewModel)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendButton(viewModel: EbayViewModel = viewModel(factory=EbayViewModel.Factory)) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(10.dp)
    ) {
        OutlinedTextField(
            value = viewModel.messageWindowMessage,
            onValueChange = { viewModel.messageWindowMessage = it },
            Modifier.weight(1f),
            placeholder = {
                Text(
                    "Nachricht schreiben",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,

            ),
            enabled = !viewModel.addWindowSending,


            )
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(onClick = { viewModel.sendMessageFromMessageBox() } ) {
            Icon(
                imageVector = Icons.Default.Send, contentDescription = null,
                tint = if (viewModel.messageWindowMessage.length > 1 && !viewModel.messageWindowSending) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Composable
fun HisMessage(
    navController: NavHostController,
    message: Message,
    viewModel: EbayViewModel,
    activeConversation: Conversation
) {
    val scope = rememberCoroutineScope()
    val cardRadius: Int = 15
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HisInitialCircle(text =viewModel.getHisInitials(activeConversation) , size = "sm", modifier = Modifier.clickable {
            scope.launch {
                viewModel.activeUserLink = viewModel.getHisLink(activeConversation)
                viewModel.getUserData()
                navController.navigate("user")
            }
        })
        Column(
            Modifier
                .weight(1f)
                .padding(start = 10.dp, end = 85.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Card(
                Modifier.padding(),
                shape = RoundedCornerShape(
                    topEndPercent = cardRadius,
                    topStartPercent = cardRadius,
                    bottomEndPercent = cardRadius,
                    bottomStartPercent = 0
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Text(
                    message.textShort,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(18.dp, 12.dp),
                )
            }
            Text(
                message.readableDate, style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
fun MyMessage(msg: Message) {
    val cardRadius: Int = 15

    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 85.dp, end = 10.dp),
        horizontalAlignment = Alignment.End
    ) {

        Card(
            Modifier,
            shape = RoundedCornerShape(
                topEndPercent = cardRadius,
                topStartPercent = cardRadius,
                bottomStartPercent = cardRadius,
                bottomEndPercent = 0
            ),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                msg.textShort,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(18.dp, 12.dp),
            )
        }
        Text(
            msg.readableDate, style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 2.dp)
        )

    }
}

//
//@Preview
//@Composable
//fun ShowMessageWindow() {
//    val navController = rememberNavController()
//    val setCurrent: (String) -> Unit = { s: String -> { var a = s } }
//    EbayKleinanzeigenZakirTheme() {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            MessageWindow(navController, viewModel)
//
//        }
//
//    }
//}