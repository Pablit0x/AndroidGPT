package com.ps.androidgpt.presentation.chat_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ps.androidgpt.R
import com.ps.androidgpt.data.local.entity.ChatEntryEntity
import com.ps.androidgpt.domain.model.ChatEntry
import com.ps.androidgpt.domain.model.UserSettings
import com.ps.androidgpt.presentation.composables.ChatEntryItem
import com.ps.androidgpt.presentation.composables.MyNavigationDrawer
import com.ps.androidgpt.presentation.composables.MyTopAppBar
import com.ps.androidgpt.presentation.composables.gradientSurface
import com.ps.androidgpt.presentation.navigation.Screen
import com.ps.androidgpt.utils.Constants
import com.ps.androidgpt.utils.dataStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    state: ChatState,
    onSendRequest: (UserSettings, String) -> Unit,
    onSaveEntry: (ChatEntry) -> ChatEntryEntity,
    onDeleteEntry: (String) -> Unit,
    navController: NavController,
    drawerState: DrawerState
) {

    var chatQuery by rememberSaveable {
        mutableStateOf("")
    }

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val snackBarState by remember { mutableStateOf(SnackbarHostState()) }
    val scope = rememberCoroutineScope()

    val lazyColumnListState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var apiKey: String by remember { mutableStateOf(Constants.INVALID_API_KEY) }
    var model: String by remember { mutableStateOf(Constants.DEFAULT_MODEL_ID) }

    LaunchedEffect(Unit) {
        context.dataStore.data.collect { userSettings ->
            apiKey = userSettings.apiKey
            model = userSettings.model
        }
    }

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
            onItemClick = { destination ->
                navController.navigate(destination)
            })
    }) {
        Scaffold(snackbarHost = { SnackbarHost(snackBarState) }, topBar = {
            MyTopAppBar(
                title = stringResource(id = R.string.app_name), drawerState = drawerState
            )
        }, modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (state.chatEntries.isNullOrEmpty() && !state.isLoading) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.start_conversation_placeholder),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                } else {
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
                                        val chatEntryEntity = onSaveEntry(entry)

                                        scope.launch {
                                            when (snackBarState.showSnackbar(
                                                message = context.getString(
                                                    R.string.saved
                                                ), actionLabel = context.getString(R.string.undo)
                                            )) {
                                                SnackbarResult.ActionPerformed -> {
                                                    onDeleteEntry(chatEntryEntity.id.toHexString())
                                                }

                                                SnackbarResult.Dismissed -> {
                                                }
                                            }
                                        }
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
                                onSendRequest(
                                    UserSettings(
                                        model = model, apiKey = apiKey
                                    ), chatQuery
                                )
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