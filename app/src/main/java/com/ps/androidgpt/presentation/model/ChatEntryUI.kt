package com.ps.androidgpt.presentation.model

import com.ps.androidgpt.domain.model.ChatEntry

data class ChatEntryUI(
    val response: String,
    val query: String,
    val time: String
)

fun ChatEntryUI.toChatEntry(): ChatEntry {
    val chatEntry = ChatEntry()
    chatEntry.response = response
    chatEntry.query = query
    return chatEntry
}