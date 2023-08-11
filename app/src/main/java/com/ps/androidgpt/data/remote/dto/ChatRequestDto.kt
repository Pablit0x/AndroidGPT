package com.ps.androidgpt.data.remote.dto

data class ChatRequestDto(
    val model: String,
    val messages: List<ChatMessageDto>
)
