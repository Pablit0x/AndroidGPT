package com.ps.androidgpt.presentation.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    drawerState: DrawerState,
    actionIcon: ImageVector? = null,
    onActionClick: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    CenterAlignedTopAppBar(title = {
        Text(
            text = title, fontSize = 24.sp, fontWeight = FontWeight.Bold
        )
    }, scrollBehavior = scrollBehavior, navigationIcon = {
        IconButton(onClick = {
            scope.launch {
                drawerState.open()
            }
        }) {
            Icon(imageVector = Icons.Default.Menu, contentDescription = null)
        }
    },
        actions = {
            if (actionIcon != null) {
                IconButton(onClick = { onActionClick() }) {
                    Icon(imageVector = actionIcon, contentDescription = null)
                }
            }
        })
}