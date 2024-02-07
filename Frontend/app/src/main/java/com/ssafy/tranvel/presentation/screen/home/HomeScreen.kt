package com.ssafy.tranvel.presentation.screen.home

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.tranvel.data.model.dto.HistoryDto
import com.ssafy.tranvel.presentation.screen.room.RoomViewModel
import com.ssafy.tranvel.presentation.screen.history.HistoryViewModel
import com.ssafy.tranvel.presentation.screen.home.component.HomeBody
import com.ssafy.tranvel.presentation.screen.home.component.HomeHeader
import com.ssafy.tranvel.presentation.screen.home.component.HomeRoomBody
import com.ssafy.tranvel.presentation.screen.mainMenuDrawer.MainMenuDrawerScreen

@Composable
fun HomeScreen(
//    roomViewModel: RoomViewModel,
    navController: NavController,
    historyViewModel: HistoryViewModel,
    onSettingClicked: () -> Unit,
    onAnnouncementClicked: () -> Unit,
    onWithdrawalClicked: () -> Unit,
    onEnterButtonClicked: () -> Unit,
    onCreateButtonClicked: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val navController = rememberNavController()
//    navController.navigate

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight()
            ) {
                MainMenuDrawerScreen(
                    onSettingClicked = {
                        onSettingClicked()
                    },
                    onAnnouncementClicked = {
                        onAnnouncementClicked()
                    },
                    onInquiryClicked = {

                    },
                    onWithdrawalClicked = {
                        onWithdrawalClicked()
                    }
                ) {

                }
            }
        },
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = { HomeHeader(drawerState = drawerState) },
            content = { paddingValues ->
                HomeBody(
                    paddingValues,
//                    roomViewModel,
                    historyViewModel,
                    onEnterButtonClicked,
                    onCreateButtonClicked,
                    navController
                )
            }
        )
    }
}
