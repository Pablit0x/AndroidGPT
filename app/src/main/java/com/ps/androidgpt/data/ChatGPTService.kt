package com.ps.androidgpt.data

import com.ps.androidgpt.presentation.chat_screen.ChatCompletion
import com.ps.androidgpt.presentation.chat_screen.ChatRequest
import com.ps.androidgpt.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatGPTService {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer ${Constants.API_KEY}" // Replace with your actual API key
    )
    @POST("v1/chat/completions")
    suspend fun getChatCompletion(@Body request: ChatRequest): Response<ChatCompletion>
}