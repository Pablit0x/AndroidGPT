package com.ps.androidgpt.presentation.saved_chats_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ps.androidgpt.R
import com.ps.androidgpt.domain.model.ChatEntry
import com.ps.androidgpt.domain.model.matchesSearch
import com.ps.androidgpt.presentation.composables.MyNavigationDrawer
import com.ps.androidgpt.presentation.composables.MyTopAppBar
import com.ps.androidgpt.presentation.composables.SavedChatEntryItem
import com.ps.androidgpt.presentation.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class)
@Composable
fun SavedChatsScreen(
    chatEntries: List<ChatEntry>?,
    onDelete: (String) -> Unit,
    navController: NavController,
    drawerState: DrawerState
) {
    val clipboardManager = LocalClipboardManager.current
    val lazyColumnListState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }


    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        MyNavigationDrawer(modifier = Modifier.fillMaxWidth(0.5f),
            currentScreenId = Screen.SavedEntriesScreen.id,
            onItemClick = { destination ->
                navController.navigate(destination)
            })
    }) {
        Scaffold(
            topBar = {
                MyTopAppBar(title = stringResource(id = R.string.saved),
                    scrollBehavior = scrollBehavior,
                    drawerState = drawerState,
                    actionIcon = if (chatEntries.isNullOrEmpty()) null else Icons.Default.Search,
                    onActionClick = { isSearchActive = !isSearchActive })
            }, modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (chatEntries.isNullOrEmpty()) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.empty_saved),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.outline
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        ElevatedButton(onClick = {
                            navController.navigate(Screen.HomeScreen.route)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(stringResource(id = R.string.open_chat))
                        }
                    }
                } else {
                    AnimatedVisibility(visible = isSearchActive) {
                        OutlinedTextField(value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text(text = stringResource(id = R.string.search)) },
                            trailingIcon = {
                                IconButton(onClick = {
                                    if (searchQuery.isNotEmpty()) searchQuery =
                                        "" else isSearchActive = false
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close, contentDescription = null
                                    )
                                }
                            },
                            shape = RoundedCornerShape(40),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                    AnimatedVisibility(visible = chatEntries.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            state = lazyColumnListState
                        ) {
                            items(chatEntries) { chatEntry ->
                                if (chatEntry.matchesSearch(searchQuery)) {
                                    SavedChatEntryItem(modifier = Modifier
                                        .fillMaxWidth()
                                        .animateItemPlacement(),
                                        chatEntry = chatEntry,
                                        onDeleteClick = { onDelete(it) },
                                        onCopy = {
                                            clipboardManager.setText(buildAnnotatedString {
                                                append(
                                                    it
                                                )
                                            })
                                        })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}