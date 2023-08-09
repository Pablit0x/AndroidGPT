package com.ps.androidgpt.data

import com.ps.androidgpt.BuildConfig
import com.ps.androidgpt.domain.model.ChatCompletion
import com.ps.androidgpt.domain.model.ChatRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatApi {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer ${BuildConfig.API_KEY}"
    )
    @POST("v1/chat/completions")
    suspend fun getChatCompletion(@Body request: ChatRequest): Response<ChatCompletion>
}