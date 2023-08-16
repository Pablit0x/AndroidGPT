package com.ps.androidgpt.presentation.prompts_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ps.androidgpt.R
import com.ps.androidgpt.presentation.composables.MyNavigationDrawer
import com.ps.androidgpt.presentation.composables.MyTopAppBar
import com.ps.androidgpt.presentation.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromptsScreen(navController: NavController, drawerState: DrawerState) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        MyNavigationDrawer(
            currentScreenId = Screen.PromptsScreen.id, onItemClick = { destination ->
                navController.navigate(destination)
            }, modifier = Modifier.fillMaxWidth(0.5f)
        )
    }) {
        Scaffold(topBar = {
            MyTopAppBar(
                scrollBehavior = scrollBehavior,
                title = stringResource(id = R.string.prompts),
                drawerState = drawerState
            )
        }, modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) { padding ->
            LazyColumn(
                modifier = Modifier.padding(padding)
            ) {
                items(100) {
                    Text(text = "Item $it")
                }
            }
        }

    }
}