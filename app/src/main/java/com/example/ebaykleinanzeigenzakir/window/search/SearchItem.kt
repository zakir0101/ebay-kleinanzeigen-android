package com.example.ebaykleinanzeigenzakir.window.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ebaykleinanzeigenzakir.R

import com.example.ebaykleinanzeigenzakir.ui.theme.EbayKleinanzeigenZakirTheme

@Composable
internal fun SearchItem(navController: NavHostController) {

    Row(
        Modifier
            .height(120.dp)
            .fillMaxWidth()
            .clickable { navController.navigate("add") }
            .background(color = MaterialTheme.colorScheme.surface)

    ) {
        Image(
            painter = painterResource(id = R.drawable.testing_image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .width(162.dp)
                .padding(0.dp, 1.dp)
                ,
            contentScale = ContentScale.Crop
        )
        Column(
            Modifier
                .fillMaxHeight()
                .weight(1f)
                .border(1.dp, color = MaterialTheme.colorScheme.background)
                .padding(start = 10.dp, top = 5.dp, bottom = 10.dp)
        ) {
            Text(text = "Augsburg", color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall)

            Text(text = "Microsoft Surface Book 3 (in Sehr guten Zustand)",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = "280 $",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.weight(1f))


            Text(text = "Versand möglich",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        RoundedCornerShape(50)
                    )
                    .padding(6.dp, 2.dp),

            )
        }
    }
}


@Preview
@Composable
fun SeeSearchItem() {
    val navController = rememberNavController()
    EbayKleinanzeigenZakirTheme {
        SearchItem(navController)
    }
}