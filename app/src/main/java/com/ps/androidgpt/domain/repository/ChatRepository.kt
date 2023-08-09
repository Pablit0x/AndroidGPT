package com.ps.androidgpt.domain.repository

import com.ps.androidgpt.domain.model.ChatCompletion
import com.ps.androidgpt.domain.model.ChatRequest
import retrofit2.Response

interface ChatRepository {
    suspend fun getChatCompletion(request: ChatRequest): Response<ChatCompletion>
}