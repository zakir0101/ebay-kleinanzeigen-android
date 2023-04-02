@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ebaykleinanzeigenzakir.window.conversation


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera


import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ebaykleinanzeigenzakir.ConversationImage
import com.example.ebaykleinanzeigenzakir.ui.theme.EbayKleinanzeigenZakirTheme

@Composable
internal fun ConversationWindow(navController: NavHostController) {
    LazyColumn() {
        items(20) {
            ConversationItem(navController)
        }
    }
}

@Composable
fun ConversationItem(navController: NavHostController) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { navController.navigate("message") }
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.background)
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ConversationImage()
        ConversationItemText()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.ConversationItemText() {

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
            Text(text = "Katja", style = commonTextStyle, color = commonTextColor)
            Text(text = "Fr. 17.03.2023", style = commonTextStyle, color = commonTextColor)
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Mantel in Sehr guten Zustand",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )

            Badge(
                modifier = Modifier.padding(top = 4.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {

                Text(text = "1", style = MaterialTheme.typography.titleSmall)
            }
        }
        Text(
            text = "Hi , dies ist meine erste Nachricht. bitte kontaktieren mich für weitere Details",
            style = commonTextStyle,
            color = commonTextColor
        )

    }

}


@Preview
@Composable
fun ShowConversationWindow() {
    val navController = rememberNavController()
    val setCurrent: (String) -> Unit = { s: String -> { var a = s } }
    EbayKleinanzeigenZakirTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ConversationWindow(navController)

        }

    }
}