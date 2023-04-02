@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ebaykleinanzeigenzakir

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.DrawerState


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ebaykleinanzeigenzakir.window.SimpleTextField
import com.example.ebaykleinanzeigenzakir.window.navigateBack
import com.example.ebaykleinanzeigenzakir.window.toggle
import kotlinx.coroutines.CoroutineScope


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EbayAppbarBoth(
    switchDarkMode: () -> Unit,
    navDrawerState: DrawerState,
    filterDrawerState: DrawerState,
    cityDrawerState: DrawerState,
    categoryDrawerState: DrawerState,
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController,
    darkMode: Boolean,

    ) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val current = navBackStackEntry?.destination?.route

    val showSearchbar = current !in arrayOf("publish", "conversation", "setting")

    Column() {

        if (current == "conversation" || current == "publish" || current == "setting")
            EbayAppbarTopMedium(switchDarkMode, darkMode, navDrawerState, current, scrollBehavior)
        else if (current == "message"  || current == "user" || current == "login")
            EbayAppbarTopSmall(switchDarkMode, darkMode, current, navController,navDrawerState)
        else if(current == "main" || current == "search" )
            EbayAppbarTopCentered(
                switchDarkMode,
                darkMode,
                navDrawerState,
                true,
                scrollBehavior
            )

        if (current == "search") {
            EbayAppbarBottom(filterDrawerState, cityDrawerState, categoryDrawerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EbayAppbarTopSmall(
    switchDarkMode: () -> Unit,
    darkMode: Boolean,
    current: String,
    navController: NavHostController,
    navDrawerState: DrawerState,
) {
    TopAppBar(
        title = { when (current ){
           "message" -> MessageWindowTitle()
            "user" -> UserWindowTitle()
            "login" -> Text("Login",style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary)
        } },
        navigationIcon = { if (current=="login") EbayNavigationIcon(navDrawerState) else
                                EbayNavigateBackIcon(navController) },
        actions = { SwitchDarkModeIcon(switchDarkMode, darkMode) },
        colors = getCenteredTopAppbarColor()
    )




}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EbayAppbarTopMedium(
    switchDarkMode: () -> Unit, darkMode: Boolean,
    navDrawerState: DrawerState,
    current: String,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val title: String = when ( current) {
        "conversation" ->  "Nachrichten"
        "publish" -> "Anzeige erstellen"
        "setting" -> "Einstellung"
        else -> {""}
    }
    MediumTopAppBar(
        scrollBehavior = scrollBehavior,
        title = { TitleText(title = title) },
        navigationIcon = { EbayNavigationIcon(navDrawerState) },
        actions = { SwitchDarkModeIcon(switchDarkMode, darkMode) },
        colors = getCenteredTopAppbarColor()
    )

}



@Composable
fun AddWindowTopAppBar(
    appbarColorAlpha: Float,
    navController: NavHostController,
    switchDarkMode: () -> Unit,
    darkMode: Boolean
) {
    TopAppBar(

        modifier = Modifier.zIndex(2f),
        title = { TitleText(title = if (appbarColorAlpha < 1f) "" else "Original Samsumg Boo..") },
        navigationIcon = { EbayNavigateBackIcon(navController) },
        actions = { SwitchDarkModeIcon(switchDarkMode, darkMode) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(
                alpha = appbarColorAlpha
            ),
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EbayAppbarTopCentered(
    switchDarkMode: () -> Unit,
    darkMode: Boolean,
    navDrawerState: DrawerState,
    showSearchBar: Boolean,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            if (showSearchBar) SearchBar()
        },
        navigationIcon = { EbayNavigationIcon(navDrawerState) },
        actions = { SwitchDarkModeIcon(switchDarkMode, darkMode) },
        colors = getCenteredTopAppbarColor()

    )

}


@Composable
fun UserWindowTitle() {
    Column(
        Modifier) {
        Text(text = "Nesrin Ünal", color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge)
        Text(text = "38 Anzeigen online - 143 Anzeigen gesamt",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium)

    }
}

@Composable
fun MessageWindowTitle() {
    Row(Modifier, verticalAlignment = Alignment.CenterVertically) {
        ConversationImage(size = 45)
        Column(
            Modifier
                .weight(1f)
                .padding(start = 12.dp) ) {
            Text(text = "Mantel in sehr guten Zustand", color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleSmall)
            Text(text = "Katja", color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodySmall)

        }
    }
}


@Composable
fun TitleText(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun getCenteredTopAppbarColor(): TopAppBarColors {
    return TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun getMediumTopAppbarColor(): TopAppBarColors {
    return TopAppBarDefaults.mediumTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
    )
}


@Composable
fun SwitchDarkModeIcon(switchDarkMode: () -> Unit, darkMode: Boolean) {
    val icon = if (darkMode) Icons.Filled.LightMode else Icons.Filled.DarkMode


    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = Modifier
            .padding(end = 10.dp)
            .clickable { switchDarkMode() }
    )
}

@Composable
fun EbayNavigationIcon(navDrawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    IconButton(onClick = { navDrawerState.toggle(scope) }
    ) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = null,
            modifier = Modifier.padding(start = 10.dp)
        )

    }
}

@Composable
fun EbayNavigateBackIcon(navController: NavHostController) {
    val scope = rememberCoroutineScope()

    IconButton(onClick = { navController.navigateBack() }
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier.padding(start = 10.dp)
        )

    }
}


@Composable
fun EbayAppbarBottom(
    filterDrawerState: DrawerState,
    cityDrawerState: DrawerState,
    categoryDrawerState: DrawerState
) {
    val scope = rememberCoroutineScope()

    Row(
        Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ButtonFilter(filterDrawerState, scope)
        ButtonCity(cityDrawerState, scope)
        ButtonCategory(categoryDrawerState, scope)
    }
}

@Composable
fun ButtonFilter(filterDrawerState: DrawerState, scope: CoroutineScope) {
    OutlinedButton(
        onClick = { filterDrawerState.toggle(scope) },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black.copy(alpha = 0.2f),
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        modifier = Modifier,
        border = BorderStroke(0.3.dp, MaterialTheme.colorScheme.secondary),
        contentPadding = PaddingValues(10.dp, 5.dp)
    )
    {


        Icon(
            imageVector = Icons.Default.CheckCircle, contentDescription = null,

            )
        Spacer(modifier = Modifier.width(5.dp))
        Text("Filter", lineHeight = 0.sp, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.width(5.dp))
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null,
            Modifier.size(13.dp)
        )

    }

}

@Composable
fun ButtonCity(cityDrawerState: DrawerState, scope: CoroutineScope) {
    OutlinedButton(
        onClick = { cityDrawerState.toggle(scope) },
        colors = ButtonDefaults.buttonColors(),
        contentPadding = PaddingValues(10.dp, 5.dp),
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.onPrimary),

        )
    {

        Text("Deutschland ", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.width(5.dp))
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null,
            Modifier.size(13.dp)
        )

    }

}

@Composable
fun ButtonCategory(categoryDrawerState: DrawerState, scope: CoroutineScope) {

    OutlinedButton(
        onClick = { categoryDrawerState.toggle(scope) },
        colors = ButtonDefaults.buttonColors(),
        contentPadding = PaddingValues(10.dp, 5.dp),
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.onPrimary),

        )
    {

        Text("alle Kategorien ", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.width(5.dp))
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null,
            Modifier.size(13.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var text by remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .padding(20.dp, 0.dp)
            .height(40.dp)
            .border(0.5.dp, MaterialTheme.colorScheme.background, MaterialTheme.shapes.medium),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(0.dp)
                .fillMaxHeight()
        ) {
            SimpleTextField(
                value = text, onValueChange = { text = it },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(10.dp, 0.dp),
                textColor = MaterialTheme.colorScheme.onBackground,
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.background,

                ),


                textStyle = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                singleLine = true,
                placeholderText = "Suche in ganz Deutschland"
            )
            IconButton(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(40.dp))
                    .fillMaxHeight(),
                onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Default.Search, contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

//
//@Preview(showBackground = false)
//@Composable
//fun ShowSearchbar() {
//    var drawerState: androidx.compose.material.DrawerState = rememberDrawerState(initialValue = DrawerValue.Open)
//    var scope = rememberCoroutineScope()
//    EbayKleinanzeigenZakirTheme(darkTheme = false) {
////d        EbayAppbarBoth({}, {}, "search", null)
//    ButtonFilter(filterDrawerState =drawerState , scope =scope )
//    }
//}
