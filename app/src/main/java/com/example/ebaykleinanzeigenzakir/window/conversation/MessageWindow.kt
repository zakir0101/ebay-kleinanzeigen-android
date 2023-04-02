package com.example.ebaykleinanzeigenzakir.window.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ebaykleinanzeigenzakir.HisInitialCircle
import com.example.ebaykleinanzeigenzakir.ui.theme.EbayKleinanzeigenZakirTheme
import kotlinx.coroutines.launch

class Message(val msg: String, val isMe: Boolean)

fun getMessages(): List<Message> {
    return listOf<Message>(
        Message("hallo", true),
        Message("Hallo", false),
        Message("Wie geht es dir", true),
        Message("gut , danke , und dir", false),
        Message("auch gut , hey wollte dich fragen ob du mir bei eine Sache Helfen kannst", true),
        Message("ja gerne", false),
        Message("okay ", true),
        Message(
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et  ",
            false
        ),
        Message("kein Problem  ", true),
        Message("also hast du Zeit  ", false),
        Message(
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore e",
            true
        ),
        Message("lorem  ", false),
        Message("kein Problem  ", true),
        Message("also hast du Zeit  ", false),
        Message(
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore e",
            true
        ),
        Message("letzte Nachricht  ", false),

        )
}

@Composable
fun MessageWindow(navController: NavHostController) {
    val messages = getMessages()
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    Column(Modifier.fillMaxSize()) {


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
            itemsIndexed(items = getMessages()) { index, it ->
                if (it.isMe)
                    MyMessage(it.msg)
                else
                    HisMessage(msg = it.msg, navController)

                Spacer(modifier = Modifier.height(15.dp))
                if (index == (messages.size - 1))
                    Spacer(modifier = Modifier.height(15.dp))
            }
        }


        SendButton()

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendButton() {
    var value by remember { mutableStateOf("") }
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(10.dp)
    ) {
        OutlinedTextField(
            value = value, onValueChange = { value = it },
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
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Send, contentDescription = null,
                tint = if (value.length > 1) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Composable
fun HisMessage(msg: String, navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val cardRadius: Int = 20
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HisInitialCircle(text = "K", size = "sm", modifier = Modifier.clickable {
            scope.launch {
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
                Modifier,
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
                    msg,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(10.dp, 12.dp),
                )
            }
            Text(
                "17:40 5 Oct 2022", style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
fun MyMessage(msg: String) {
    val cardRadius: Int = 20

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
                msg,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(10.dp, 12.dp),
            )
        }
        Text(
            "17:40 5 Oct 2022", style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 2.dp)
        )

    }
}


@Preview
@Composable
fun ShowMessageWindow() {
    val navController = rememberNavController()
    val setCurrent: (String) -> Unit = { s: String -> { var a = s } }
    EbayKleinanzeigenZakirTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MessageWindow(navController)

        }

    }
}