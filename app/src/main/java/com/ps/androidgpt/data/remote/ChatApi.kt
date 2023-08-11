package com.ps.androidgpt.data.remote

import com.ps.androidgpt.BuildConfig
import com.ps.androidgpt.data.remote.dto.ChatCompletion
import com.ps.androidgpt.data.remote.dto.ChatRequestDto
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatApi {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer ${BuildConfig.API_KEY}"
    )
    @POST("v1/chat/completions")
    suspend fun getChatCompletion(@Body request: ChatRequestDto): ChatCompletion
}