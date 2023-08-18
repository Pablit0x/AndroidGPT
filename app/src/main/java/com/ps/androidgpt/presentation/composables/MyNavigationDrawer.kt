package com.ps.androidgpt.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Adjust
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ps.androidgpt.R
import com.ps.androidgpt.presentation.navigation.Screen
import com.ps.androidgpt.utils.Constants

data class DrawerMenuItem(
    val id: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: String,
    val contentDescription: String,
    val onClickAction: () -> Unit
)


@Composable
fun MyNavigationDrawer(
    modifier: Modifier = Modifier, currentScreenId: Int, onItemClick: (destination: String) -> Unit
) {
    val menuItems = listOf(
        DrawerMenuItem(
            Screen.HomeScreen.id,
            Icons.Filled.Home,
            Icons.Outlined.Home,
            stringResource(id = R.string.home),
            stringResource(id = R.string.navigate_home),
            onClickAction = {
                onItemClick("${Screen.HomeScreen.route}/ ")
            }), DrawerMenuItem(
            Screen.SavedEntriesScreen.id,
            Icons.Filled.Bookmark,
            Icons.Outlined.BookmarkBorder,
            stringResource(id = R.string.saved),
            stringResource(id = R.string.navigate_saved),
            onClickAction = {
                onItemClick(Screen.SavedEntriesScreen.route)
            }), DrawerMenuItem(
            Screen.PromptsScreen.id,
            Icons.Filled.Adjust,
            Icons.Outlined.Adjust,
            stringResource(id = R.string.prompts),
            stringResource(id = R.string.navigate_prompts),
            onClickAction = {
                onItemClick(Screen.PromptsScreen.route)
            }), DrawerMenuItem(
            Screen.SettingsScreen.id,
            Icons.Filled.Settings,
            Icons.Outlined.Settings,
            stringResource(id = R.string.settings),
            stringResource(id = R.string.navigate_settings),
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
                    imageVector = if (currentScreenId == item.id) item.selectedIcon else item.unselectedIcon,
                    contentDescription = item.contentDescription
                )
            }, label = { Text(item.title) }, selected = currentScreenId == item.id, onClick = {
                item.onClickAction()
            })
        }
    }
}
