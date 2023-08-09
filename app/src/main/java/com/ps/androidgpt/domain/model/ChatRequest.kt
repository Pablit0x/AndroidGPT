package com.ps.androidgpt.domain.model

data class ChatRequest(
    val model: String,
    val messages: List<ChatMessage>
)
