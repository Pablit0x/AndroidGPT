package com.ps.androidgpt.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ps.androidgpt.presentation.navigation.Screen

data class DrawerMenuItem(
    val id: Int,
    val icon: ImageVector,
    val title: String,
    val contentDescription: String,
    val onClickAction: () -> Unit
)


@Composable
fun MyNavigationDrawer(
    modifier: Modifier = Modifier, currentScreenId: Int, onItemClick: (destination: String) -> Unit
) {
    val menuItems =
        listOf(
            DrawerMenuItem(0, Icons.Default.Home, "Home", "Navigate to home", onClickAction = {
                onItemClick(Screen.HomeScreen.route)
            }),
            DrawerMenuItem(
                1,
                Icons.Default.Bookmark,
                "Saved",
                "Navigate to saved",
                onClickAction = {
                    onItemClick(Screen.SavedEntriesScreen.route)
                }),
            DrawerMenuItem(
                2,
                Icons.Default.Adjust,
                "Prompts",
                "Navigate to prompts",
                onClickAction = {
                    onItemClick(Screen.HomeScreen.route)
                }),
            DrawerMenuItem(3,
                Icons.Default.Settings,
                "Settings",
                "Navigate to settings",
                onClickAction = {
                    onItemClick(Screen.SettingsScreen.route)
                })
        )

    ModalDrawerSheet(
        modifier = modifier
    ) {

        menuItems.forEach { item ->
            NavigationDrawerItem(modifier = Modifier.padding(4.dp), icon = {
                Icon(
                    imageVector = item.icon, contentDescription = item.contentDescription
                )
            }, label = { Text(item.title) }, selected = currentScreenId == item.id, onClick = {
                item.onClickAction()
            })
        }
    }
}
