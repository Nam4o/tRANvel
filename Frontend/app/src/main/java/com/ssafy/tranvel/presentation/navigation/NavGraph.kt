package com.ssafy.tranvel.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssafy.tranvel.presentation.screen.announcement.navigation.announcementScreen
import com.ssafy.tranvel.presentation.screen.found.FoundPasswordScreen
import com.ssafy.tranvel.presentation.screen.home.navigation.homeRoute
import com.ssafy.tranvel.presentation.screen.home.navigation.homeScreen
import com.ssafy.tranvel.presentation.screen.login.LoginScreen
import com.ssafy.tranvel.presentation.screen.register.navigation.registerGraph
import com.ssafy.tranvel.presentation.screen.travel.navigation.gameNavGraph
import com.ssafy.tranvel.presentation.screen.userInfoModification.navigation.userInfoModifyScreen
import com.ssafy.tranvel.presentation.screen.userWithdrawal.navigation.userWithdrawalScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login_screen",
            Modifier.padding(innerPadding)
        ) {
            announcementScreen { }
            gameNavGraph(navController)
            composable(route = "login_screen") {
                LoginScreen(
                    loginViewModel = hiltViewModel(),
                    onNavigateToRegister = {
                        navController.navigate("register")
                    },
                    onNavigateToFound = {
                        navController.navigate("found_screen")
                    },
                    onLoginButtonClicked = {
                        it.viewModelStore.clear()
                        navController.navigate("home_route") {
                            popUpTo("login_screen") { inclusive = true }
                        }
                    }
                )
            }

            composable(route = "found_screen") {
                FoundPasswordScreen() {
                    navController.navigate("login_screen")
                }
            }
            registerGraph(navController)
            homeScreen(navController) { }
            userInfoModifyScreen(navController)
            userWithdrawalScreen(navController)
        }
    }
}
