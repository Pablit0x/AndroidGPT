package com.ps.androidgpt.presentation.chat_screen

data class ChatState(
    val response: List<String> = listOf(),
    val isLoading: Boolean = false
)