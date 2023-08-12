package com.ps.androidgpt.presentation.saved_chats_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ps.androidgpt.R
import com.ps.androidgpt.presentation.chat_screen.ChatState
import com.ps.androidgpt.presentation.composables.SavedChatEntryItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SavedChatsScreen(
    state: ChatState, onDelete: (String) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current

    val lazyColumnListState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.saved),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }, scrollBehavior = scrollBehavior
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
                state = lazyColumnListState
            ) {
                items(state.chatEntries) { chatEntry ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        SavedChatEntryItem(modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement(),
                            chatEntry = chatEntry,
                            onDeleteClick = { onDelete(it) },
                            onCopy = { clipboardManager.setText(buildAnnotatedString { append(it) }) })
                    }
                }
            }
        }
    }
}