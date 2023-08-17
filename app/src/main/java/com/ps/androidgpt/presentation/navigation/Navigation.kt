package com.ps.androidgpt.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.ps.androidgpt.presentation.chat_screen.ChatScreen
import com.ps.androidgpt.presentation.chat_screen.ChatViewModel
import com.ps.androidgpt.presentation.prompts_screen.PromptViewModel
import com.ps.androidgpt.presentation.prompts_screen.PromptsScreen
import com.ps.androidgpt.presentation.saved_chats_screen.SavedChatsScreen
import com.ps.androidgpt.presentation.saved_chats_screen.SavedResponsesViewModel
import com.ps.androidgpt.presentation.settings_screen.SettingScreen
import com.ps.androidgpt.utils.Constants

@Composable
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun NavGraph() {
    val navController = rememberAnimatedNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    AnimatedNavHost(navController = navController,
        startDestination = "${Screen.HomeScreen.route}/{${Constants.PROMPT_NAVIGATION_ARGUMENT}}",
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = { ExitTransition.None }) {
        composable(
            route = "${Screen.HomeScreen.route}/{${Constants.PROMPT_NAVIGATION_ARGUMENT}}",
            arguments = listOf(navArgument(
                Constants.PROMPT_NAVIGATION_ARGUMENT
            ) {
                type = NavType.StringType
            })
        ) { entry ->
            val chatViewModel = hiltViewModel<ChatViewModel>()
            val state by chatViewModel.state.collectAsStateWithLifecycle()
            val prompt = entry.arguments?.getString(Constants.PROMPT_NAVIGATION_ARGUMENT)

            ChatScreen(
                state = state,
                prompt = prompt,
                onSendRequest = chatViewModel::getChatResponse,
                onSaveEntry = chatViewModel::insertChatEntry,
                onDeleteEntry = chatViewModel::deleteEntry,
                navController = navController,
                drawerState = drawerState
            )
        }

        composable(
            route = Screen.SavedEntriesScreen.route
        ) {
            val savedResponsesViewModel = hiltViewModel<SavedResponsesViewModel>()
            val chatEntries by savedResponsesViewModel.chatEntries.collectAsStateWithLifecycle()
            SavedChatsScreen(
                chatEntries = chatEntries,
                onDelete = savedResponsesViewModel::deleteEntry,
                navController = navController,
                drawerState = drawerState
            )
        }

        composable(route = Screen.SettingsScreen.route) {
            SettingScreen(
                navController = navController, drawerState = drawerState
            )
        }

        composable(route = Screen.PromptsScreen.route) {
            val promptViewModel = hiltViewModel<PromptViewModel>()
            val prompts by promptViewModel.prompts.collectAsStateWithLifecycle()

            PromptsScreen(
                prompts = prompts,
                onInsertPrompt = promptViewModel::insertPrompt,
                navController = navController,
                drawerState = drawerState
            )
        }
    }
}

