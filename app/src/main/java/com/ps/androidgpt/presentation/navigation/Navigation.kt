package com.ps.androidgpt.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ps.androidgpt.presentation.chat_screen.ChatScreen
import com.ps.androidgpt.presentation.chat_screen.ChatViewModel
import com.ps.androidgpt.presentation.saved_chats_screen.SavedChatsScreen
import com.ps.androidgpt.presentation.saved_chats_screen.SavedChatsViewModel

@Composable
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
    ) {
        composable(route = Screen.HomeScreen.route) {

            val chatViewModel = hiltViewModel<ChatViewModel>()
            val state by chatViewModel.state.collectAsStateWithLifecycle()


            ChatScreen(state = state,
                onSendRequest = chatViewModel::getChatResponse,
                onSaveEntry = chatViewModel::insertChatEntry,
                navigate = { navController.navigate(Screen.SavedEntriesScreen.route) })

        }

        composable(route = Screen.SavedEntriesScreen.route) {
            val savedChatsViewModel = hiltViewModel<SavedChatsViewModel>()
            val state by savedChatsViewModel.state.collectAsStateWithLifecycle()
            SavedChatsScreen(state = state, onDelete = savedChatsViewModel::deleteEntry)
        }
    }

}