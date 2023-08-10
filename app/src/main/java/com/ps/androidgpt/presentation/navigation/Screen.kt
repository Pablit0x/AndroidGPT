package com.ps.androidgpt.presentation.navigation

import com.ps.androidgpt.utils.Constants

sealed class Screen(val route : String){
    object HomeScreen : Screen(route = Constants.HOME_SCREEN)
}
