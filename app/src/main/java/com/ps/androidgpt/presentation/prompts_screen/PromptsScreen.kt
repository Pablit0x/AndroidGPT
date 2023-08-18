package com.ps.androidgpt.presentation.prompts_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ps.androidgpt.R
import com.ps.androidgpt.domain.model.PromptEntry
import com.ps.androidgpt.presentation.composables.MyNavigationDrawer
import com.ps.androidgpt.presentation.composables.MyTopAppBar
import com.ps.androidgpt.presentation.composables.PromptItem
import com.ps.androidgpt.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromptsScreen(
    prompts: List<PromptEntry>?,
    onInsertPrompt: (PromptEntry) -> Unit,
    onDeletePrompt: (String) -> Unit,
    onEditPrompt: (PromptEntry) -> Unit,
    navController: NavController,
    drawerState: DrawerState
) {
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
        }, floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.UpsertPromptScreen.route + "/ ")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }, modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) { padding ->

            if (prompts != null) {
                LazyColumn(
                    modifier = Modifier.padding(padding),
                    contentPadding = PaddingValues(bottom = 76.dp)
                ) {
                    items(prompts) {
                        PromptItem(prompt = it.prompt, onSelect = {
                            navController.navigate("${Screen.HomeScreen.route}/${it.prompt}")
                        }, onEdit = {
                            navController.navigate("${Screen.UpsertPromptScreen.route}/${it.prompt}")
                        }, onRemove = {
                            it.id?.let(onDeletePrompt)
                        })
                    }
                }
            }
        }
    }
}