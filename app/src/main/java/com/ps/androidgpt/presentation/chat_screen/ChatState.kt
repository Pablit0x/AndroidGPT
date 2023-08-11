package com.ps.androidgpt.presentation.chat_screen

import com.ps.androidgpt.presentation.model.ChatEntryUI

data class ChatState(
    val chatEntries: List<ChatEntryUI> = listOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)
