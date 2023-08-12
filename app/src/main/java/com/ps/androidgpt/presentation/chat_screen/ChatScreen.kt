package com.ps.androidgpt.presentation.chat_screen

import android.widget.Toast
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ps.androidgpt.R
import com.ps.androidgpt.domain.model.ChatEntry
import com.ps.androidgpt.presentation.composables.ChatEntryItem
import com.ps.androidgpt.presentation.composables.MyTopAppBar
import com.ps.androidgpt.presentation.composables.MyNavigationDrawer
import com.ps.androidgpt.presentation.composables.gradientSurface
import com.ps.androidgpt.presentation.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    state: ChatState,
    onSendRequest: (String) -> Unit,
    onSaveEntry: (ChatEntry) -> Unit,
    navController: NavController
) {

    var chatQuery by rememberSaveable {
        mutableStateOf("")
    }

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val lazyColumnListState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)


    LaunchedEffect(state) {
        lazyColumnListState.animateScrollBy(1000f)
    }

    LaunchedEffect(state.error) {
        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }


    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        MyNavigationDrawer(modifier = Modifier.fillMaxWidth(0.7f),
            currentScreenId = Screen.HomeScreen.id,
            onItemClick = {destination ->
                scope.launch { drawerState.close() }
                navController.navigate(destination)
            })
    }) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    title = stringResource(id = R.string.app_name),
                    drawerState = drawerState
                )
            }, modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = lazyColumnListState,
                    reverseLayout = true
                ) {
                    items(state.chatEntries) { chatEntry ->
                        Column(modifier = Modifier.fillMaxWidth()) {
                            ChatEntryItem(modifier = Modifier.fillMaxWidth(),
                                chatEntry = chatEntry,
                                onCopyClick = {
                                    clipboardManager.setText(buildAnnotatedString { append(it) })
                                },
                                onSaveClick = { entry ->
                                    onSaveEntry(entry)
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.saved),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                })
                        }
                    }

                    if (state.isLoading) {
                        item {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .progressSemantics()
                                        .scale(0.5f)
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    OutlinedTextField(
                        shape = RoundedCornerShape(40),
                        value = chatQuery,
                        onValueChange = { chatQuery = it },
                        trailingIcon = {
                            IconButton(onClick = {
                                onSendRequest(chatQuery)
                                chatQuery = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Send, contentDescription = null
                                )
                            }
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.enter_text))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(64.dp)
                            .clip(RoundedCornerShape(40))
                            .gradientSurface()
                    )
                }
            }
        }
    }
}