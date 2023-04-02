package com.example.ebaykleinanzeigenzakir.window.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ebaykleinanzeigenzakir.ui.theme.EbayKleinanzeigenZakirTheme
import com.example.ebaykleinanzeigenzakir.window.Toggler
import com.example.ebaykleinanzeigenzakir.window.toggle
import kotlinx.coroutines.launch


@Composable
fun CategoryDrawer(
    CategoryDrawerState: DrawerState,
    rightDrawerBgColor: Color,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    RightModalDrawer(
        drawerState = CategoryDrawerState,
        drawerBackgroundColor = rightDrawerBgColor,
        content = content,
        drawerContent = {


            CategoryHeader { CategoryDrawerState.toggle(scope) }
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(0.dp, 15.dp),

                ) {
                CategoryItem(text = "Alle Kategorien", checked = true)

                Spacer(modifier = Modifier.height(15.dp))

                for (i in 1..15)
                {
                    CategoryItem(text = "Kategorie $i", checked = false)
                }

            }
        },

        )


}


@Composable
fun CategoryItem(text: String, checked: Boolean) {

    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .border(0.5.dp, MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
            .clickable {  }
            .padding(10.dp, 7.dp)


        , verticalAlignment = Alignment.CenterVertically
        , horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (checked)
            Icon(imageVector = Icons.Default.Check, contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp))
        else
            Spacer(Modifier.size(20.dp))

        Text(text = text , color = MaterialTheme.colorScheme.onSurface,
               style = MaterialTheme.typography.bodyLarge )
    }
}


@Composable
fun CategoryHeader(onClick: Toggler) {
    Row(
        Modifier
            .height(52.dp)
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            Modifier
                .clickable { onClick() }
                .fillMaxHeight()
                .padding(horizontal = 0.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack, contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )



        }


        Text(
            text = "Kategorien",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium
        )
    }

}


@Preview
@Composable
fun ShowCatDrawer() {
    EbayKleinanzeigenZakirTheme(darkTheme = false) {
        val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Open)
        val scope = rememberCoroutineScope()

        val toggleDrawer: () -> Unit = {
            scope.launch {
                drawerState.apply {
                    if (isClosed) open() else close()
                }
            }
        }
        val rightDrawerBgColor =MaterialTheme.colorScheme.surfaceColorAtElevation(15.dp)
        CategoryDrawer(drawerState, rightDrawerBgColor) {
            Column {
                Text("Text in Bodycontext")
                Button(onClick = {
                    toggleDrawer()
                }) {
                    Text("Click to open")
                }
            }
        }
    }
}