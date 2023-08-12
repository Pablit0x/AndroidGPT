package com.ps.androidgpt.presentation.chat_screen

import com.ps.androidgpt.domain.model.ChatEntry

data class ChatState(
    val chatEntries: List<ChatEntry> = listOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)
