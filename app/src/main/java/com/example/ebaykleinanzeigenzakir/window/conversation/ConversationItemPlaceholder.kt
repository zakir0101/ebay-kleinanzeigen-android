package com.example.ebaykleinanzeigenzakir.window.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ebaykleinanzeigenzakir.ConversationImagePlaceholder
import com.example.ebaykleinanzeigenzakir.EbayPlaceholder
import com.example.ebaykleinanzeigenzakir.HisInitialCircle
import kotlinx.coroutines.launch


@Composable
fun ConversationItemPlaceholder() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.background)
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ConversationImagePlaceholder()
        ConversationItemTextPlaceholder()
    }
}



@Composable
fun RowScope.ConversationItemTextPlaceholder() {

    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(start = 20.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
           EbayPlaceholder(modifier = Modifier
               .height(10.dp)
               .fillMaxWidth(0.4f))
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            EbayPlaceholder(modifier = Modifier
                .height(15.dp)
                .fillMaxWidth(0.88f))


        }
        EbayPlaceholder(modifier = Modifier
            .height(10.dp)
            .fillMaxWidth(1f))
        EbayPlaceholder(modifier = Modifier
            .height(10.dp)
            .fillMaxWidth(0.6f))

    }

}




class MessagePlaceholder(val len : Float,val isMe : Boolean)
fun getMessagePlaceholder():List<MessagePlaceholder> {
    return listOf<MessagePlaceholder>(
        MessagePlaceholder(0.3f,true),
        MessagePlaceholder(0.8f,false),
        MessagePlaceholder(0.5f,true),
        MessagePlaceholder(0.3f,false),
        MessagePlaceholder(0.9f,true),
        MessagePlaceholder(0.3f,false),
        MessagePlaceholder(0.8f,true),
        MessagePlaceholder(0.6f,false),
        MessagePlaceholder(0.9f,true),
        MessagePlaceholder(0.3f,false),
    )
}



@Composable
fun MessageWindowPlaceholder() {
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    Column(Modifier.fillMaxSize()) {
        val msgSize = 10
        LaunchedEffect(msgSize) {
            scope.launch {
                scrollState.animateScrollToItem(msgSize-1)
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
            itemsIndexed(items = getMessagePlaceholder()) { index, it ->
                if (it.isMe)
                    MyMessagePlaceholder(it)
                else
                    HisMessagePlaceholder(it)

                Spacer(modifier = Modifier.height(15.dp))
                if (index == (msgSize - 1))
                    Spacer(modifier = Modifier.height(15.dp))
            }
        }


        SendButton()

    }

}



@Composable
fun HisMessagePlaceholder(messagePlaceholder: MessagePlaceholder) {
    val cardRadius: Int = 15
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HisInitialCircle(text ="?" , size = "sm")
        Column(
            Modifier
                .weight(1f)
                .padding(start = 10.dp, end = 85.dp),
            horizontalAlignment = Alignment.Start
        ,verticalArrangement = Arrangement.spacedBy(4.dp)

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
                EbayPlaceholder(modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth(messagePlaceholder.len))
            }
            EbayPlaceholder(modifier = Modifier.height(10.dp).fillMaxWidth(0.4f))
        }
    }

}

@Composable
fun MyMessagePlaceholder(message: MessagePlaceholder) {
    val cardRadius: Int = 15

    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 85.dp, end = 10.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(4.dp)
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
            EbayPlaceholder(modifier = Modifier
                .height(10.dp)
                .fillMaxWidth(message.len))

        }
        EbayPlaceholder(modifier = Modifier.height(10.dp).fillMaxWidth(0.4f))

    }

}
