package com.example.ebaykleinanzeigenzakir.window

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ebaykleinanzeigenzakir.EbayAppbarBoth
import com.example.ebaykleinanzeigenzakir.EbayViewModel
import com.example.ebaykleinanzeigenzakir.MainWindow
import com.example.ebaykleinanzeigenzakir.NavigationDrawer
import com.example.ebaykleinanzeigenzakir.window.aboutus.AboutUsWindow
import com.example.ebaykleinanzeigenzakir.window.add.AddWindow
import com.example.ebaykleinanzeigenzakir.window.conversation.ConversationWindow
import com.example.ebaykleinanzeigenzakir.window.conversation.MessageWindow
import com.example.ebaykleinanzeigenzakir.window.login.LoginWindow
import com.example.ebaykleinanzeigenzakir.window.myadd.MyAddWindow
import com.example.ebaykleinanzeigenzakir.window.publish.PublishWindow
import com.example.ebaykleinanzeigenzakir.window.search.*
import com.example.ebaykleinanzeigenzakir.window.search.FilterDrawer
import com.example.ebaykleinanzeigenzakir.window.setting.SettingWindow
import com.example.ebaykleinanzeigenzakir.window.user.UserWindow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

typealias Toggler = () -> Unit

fun DrawerState.toggle(scope: CoroutineScope) {


    scope.launch {
        if (isClosed) open() else close()
    }


}

fun NavController.navigateBack() {
    popBackStack()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Application(viewModel: EbayViewModel) {


    val navDrawerState = rememberDrawerState(DrawerValue.Closed)
    val filterDrawerState = rememberDrawerState(DrawerValue.Closed)
    val cityDrawerState = rememberDrawerState(DrawerValue.Closed)
    val priceDrawerState = rememberDrawerState(DrawerValue.Closed)
    val categoryDrawerState = rememberDrawerState(DrawerValue.Closed)
    val navController = rememberNavController()

    val scope = rememberCoroutineScope()

    val scrollBehaviorEnter = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scrollBehaviorPinned = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val backStackEntry by navController.currentBackStackEntryAsState()
    var activeScrollBehavior = if (backStackEntry?.destination?.route == "add")
        scrollBehaviorPinned else scrollBehaviorEnter
    val navigateTo: (String) -> Unit = {
        navDrawerState.toggle(scope)
        navController.navigate(it) {
            popUpTo("main")
        }
    }

    val rightDrawerBgColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
    val loginState by viewModel.loginState.collectAsState()
//    viewModel.isUserLogged()

    PriceDrawer(priceDrawerState, rightDrawerBgColor)
    {

        CategoryDrawer(categoryDrawerState, rightDrawerBgColor)
        {

            CityDrawer(cityDrawerState, rightDrawerBgColor)
            {
                FilterDrawer(
                    filterDrawerState,
                    cityDrawerState,
                    categoryDrawerState,
                    priceDrawerState,
                    rightDrawerBgColor
                )
                {

                    NavigationDrawer(
                        navDrawerState = navDrawerState,
                        navigateTo = navigateTo,
                        navController = navController,
                        viewModel.dynamicColor,
                        viewModel.switchDynamicColor,
                        loginState,
                        logout =   { viewModel.logout () ; navDrawerState.toggle(scope) }
                        ,viewModel = viewModel
                    ) {

                        Scaffold(
                            Modifier.nestedScroll(activeScrollBehavior.nestedScrollConnection),
                            topBar = {
                                EbayAppbarBoth(
                                    viewModel.switchDarkMode,
                                    navDrawerState,
                                    filterDrawerState,
                                    cityDrawerState,
                                    categoryDrawerState,
                                    scrollBehaviorEnter,
                                    navController, viewModel.darkMode
                                )
                            }, backgroundColor = rightDrawerBgColor

                        ) {
                            val i = it


                            if (viewModel.internetConencted && loginState != null) {

                                NavHost(
                                    navController = navController,
                                    startDestination = "main",

                                    )

                                {
                                    composable("login") { LoginWindow(navController, viewModel) }
                                    composable("main") { MainWindow(navController, viewModel) }
                                    composable("add") { AddWindow(navController, viewModel) }
                                    composable("user") { UserWindow(navController) }
                                    composable("search") { SearchWindow(navController) }


                                    composable("conversation") {
                                        if (loginState!!.is_logged) ConversationWindow(navController)
                                        else YouHaveToLogin(navController)
                                    }
                                    composable("message") { MessageWindow(navController) }
                                    composable("favorites") {
                                        if ( loginState!!.is_logged) FavoritesWindow()
                                            else YouHaveToLogin(navController)}
                                    composable("publish") {
                                        if (loginState!!.is_logged) PublishWindow(navController)
                                        else YouHaveToLogin(navController)
                                    }
                                    composable("myadd") {
                                        if (loginState!!.is_logged) MyAddWindow(navController)
                                        else YouHaveToLogin(navController)
                                    }
                                    composable("setting") {
                                        if (loginState!!.is_logged) SettingWindow()
                                        else YouHaveToLogin(navController)
                                    }
                                    composable("aboutus") { AboutUsWindow() }
                                }
                            } else if( !viewModel.internetConencted ) {
                                YouHaveToConnect(viewModel)
                            }

                        }


                    }


                }
            }
        }
    }
}


