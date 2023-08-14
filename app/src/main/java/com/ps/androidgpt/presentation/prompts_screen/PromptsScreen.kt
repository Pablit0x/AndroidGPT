package com.ps.androidgpt.presentation.prompts_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
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
fun PromptsScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (drawerState.isOpen) {
            drawerState.close()
        }
    }

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        MyNavigationDrawer(currentScreenId = Screen.PromptsScreen.id, onItemClick = { destination ->
            scope.launch { drawerState.close() }
            navController.navigate(destination)
        })
    }) {
        Scaffold(topBar = {
            MyTopAppBar(
                title = stringResource(id = R.string.prompts), drawerState = drawerState
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