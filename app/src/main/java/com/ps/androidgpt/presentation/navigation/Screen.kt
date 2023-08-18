package com.ps.androidgpt.presentation.navigation


sealed class Screen(val route: String, val id: Int) {
    object HomeScreen : Screen(route = Routes.HOME_SCREEN, id = 0)
    object SavedEntriesScreen : Screen(route = Routes.SAVED_ENTRIES_SCREEN, id = 1)
    object PromptsScreen : Screen(route = Routes.PROMPTS_SCREEN, id = 2)
    object SettingsScreen : Screen(route = Routes.SETTINGS_SCREEN, id = 3)

    object UpsertPromptScreen : Screen(route = Routes.UPSERT_PROMPT_SCREEN, id = 4)
}
