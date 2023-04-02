@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.ebaykleinanzeigenzakir

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerState
import androidx.compose.material.ModalDrawer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

fun getMenuItems(viewModel: EbayViewModel): List<MenuItem> {

    return listOf<MenuItem>(
        MenuItem("Start", "main", Icons.Default.Home) {viewModel.getMain()},
        MenuItem("Suche", "search", Icons.Default.Search) {},
        MenuItem("Nachricht", "conversation", Icons.Default.Email) {},
        MenuItem("Favoriten", "favorites", Icons.Default.Star) {},
        MenuItem("Anzeige aufgeben", "publish", Icons.Default.Send) {},
        MenuItem("Meine Anzeigen", "myadd", Icons.Default.Person) {},
        MenuItem("Einstellung", "setting", Icons.Default.Settings) {},
        MenuItem("Über uns", "aboutus", Icons.Default.Face) {},
    )
}


@Composable
fun NavigationDrawer(
    navDrawerState: DrawerState,
    navigateTo: (String) -> Unit, navController: NavController,
    dynamicColor: Boolean,
    switchDynamicColor: () -> Unit,
    loginState: LoginData?,
    logout : () -> Unit,
    viewModel: EbayViewModel ,
    content: @Composable () -> Unit
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val menuItems = getMenuItems(viewModel)

    ModalDrawer(
        drawerBackgroundColor = MaterialTheme.colorScheme.surface, drawerState = navDrawerState,
        drawerContent = {


            DrawerHeader(navigateTo,loginState)

            Column(
                modifier = Modifier.padding(top = 20.dp),
                verticalArrangement = Arrangement.Top
            ) {
                for (item in menuItems) {
                    DrawerItem(item, {item.action() ;navigateTo(item.value)}, navBackStackEntry)
                }
                if (loginState != null &&  loginState.is_logged) DrawerItem(
                    item = MenuItem("Logout","",Icons.Default.Logout,logout),
                    action = {logout()} ,
                    navBackStackEntry = navBackStackEntry )
                Spacer(modifier = Modifier.weight(1f))
                DynamicColorSwitch(dynamicColor,switchDynamicColor)
            }


        }) {
        content()
    }
}



@Composable
fun DynamicColorSwitch(dynamicColor: Boolean, switchDynamicColor: () -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
            .clickable {
                switchDynamicColor()
            }
            .padding(vertical = 12.dp, horizontal = 20.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Colorize, contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = "Dynamic Colors",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.weight(1f))
        EbaySwitch(checked = dynamicColor, setChecked = switchDynamicColor)

    }}

@Composable
fun DrawerItem(
    item: MenuItem,
    action: () -> Unit,
    navBackStackEntry: NavBackStackEntry?
) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .clickable {
                action()
            }
            .padding(vertical = 12.dp)
            .padding(start = 20.dp)
    ) {
        Icon(
            imageVector = item.icon, contentDescription = null,
            tint = if (navBackStackEntry?.destination?.route == item.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = item.name,
            color = if (navBackStackEntry?.destination?.route == item.value) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleSmall
        )

    }
}

@Composable
fun DrawerHeader(navigateTo: (String) -> Unit, loginState: LoginData?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(0),

        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 20.dp, top = 20.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Card(
                modifier = Modifier.size(62.dp),
                shape = RoundedCornerShape(50),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),

                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                    )

                }
            }

            Text(
                text = "Einloggen", style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.clickable { navigateTo("login") }
            )
        }
    }

}
