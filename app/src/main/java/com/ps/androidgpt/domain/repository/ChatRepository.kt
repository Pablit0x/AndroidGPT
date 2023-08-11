package com.ps.androidgpt.domain.repository

import com.ps.androidgpt.data.remote.dto.ChatCompletion
import com.ps.androidgpt.data.remote.dto.ChatRequestDto

interface ChatRepository {
    suspend fun getChatCompletion(request: ChatRequestDto): ChatCompletion
}