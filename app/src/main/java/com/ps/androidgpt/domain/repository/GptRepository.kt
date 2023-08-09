package com.ps.androidgpt.domain.repository

import com.ps.androidgpt.presentation.chat_screen.ChatCompletion
import com.ps.androidgpt.presentation.chat_screen.ChatRequest
import retrofit2.Response

interface GptRepository {
    suspend fun getChatCompletion(request: ChatRequest): Response<ChatCompletion>
}