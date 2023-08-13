package com.ps.androidgpt.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.ps.androidgpt.presentation.chat_screen.ChatScreen
import com.ps.androidgpt.presentation.chat_screen.ChatViewModel
import com.ps.androidgpt.presentation.saved_chats_screen.SavedChatsScreen
import com.ps.androidgpt.presentation.saved_chats_screen.SavedResponsesViewModel
import com.ps.androidgpt.presentation.settings_screen.SettingScreen

@Composable
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun NavGraph(
    navController: NavHostController
) {

    AnimatedNavHost(navController = navController,
        startDestination = Screen.HomeScreen.route,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { -1800 })
        },
        exitTransition = { ExitTransition.None }) {
        composable(route = Screen.HomeScreen.route) {

            val chatViewModel = hiltViewModel<ChatViewModel>()
            val state by chatViewModel.state.collectAsStateWithLifecycle()


            ChatScreen(
                state = state,
                onSendRequest = chatViewModel::getChatResponse,
                onSaveEntry = chatViewModel::insertChatEntry,
                navController = navController
            )

        }

        composable(route = Screen.SavedEntriesScreen.route) {
            val savedResponsesViewModel = hiltViewModel<SavedResponsesViewModel>()
            val state by savedResponsesViewModel.state.collectAsStateWithLifecycle()
            SavedChatsScreen(
                state = state,
                onDelete = savedResponsesViewModel::deleteEntry,
                navController = navController
            )
        }

        composable(route = Screen.SettingsScreen.route) {
            SettingScreen(navController = navController)
        }
    }
}

