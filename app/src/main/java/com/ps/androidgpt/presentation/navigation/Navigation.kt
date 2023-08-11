package com.ps.androidgpt.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextMotion.Companion.Animated
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.ps.androidgpt.presentation.chat_screen.ChatScreen
import com.ps.androidgpt.presentation.chat_screen.ChatViewModel

@Composable
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun NavGraph(
    navController: NavHostController
) {

    val chatViewModel = hiltViewModel<ChatViewModel>()
    val state by chatViewModel.state.collectAsStateWithLifecycle()


    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
    ) {
        composable(route = Screen.HomeScreen.route) {
            ChatScreen(
                state = state,
                onSendRequest = chatViewModel::getChatResponse,
                onSaveEntry = chatViewModel::insertChatEntry
            )
        }
    }

}