package com.ps.androidgpt.presentation.navigation

import com.ps.androidgpt.utils.Constants

sealed class Screen(val route: String, val id: Int) {
    object HomeScreen : Screen(route = Constants.HOME_SCREEN, id = 0)
    object SavedEntriesScreen : Screen(route = Constants.SAVED_ENTRIES_SCREEN, id = 1)
    object PromptsScreen : Screen(route = Constants.PROMPTS_SCREEN, id = 2)
    object SettingsScreen : Screen(route = Constants.SETTINGS_SCREEN, id = 3)
}
