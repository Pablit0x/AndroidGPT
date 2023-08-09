package com.ps.androidgpt.data.repository

import com.ps.androidgpt.data.ChatGPTService
import com.ps.androidgpt.domain.repository.GptRepository
import com.ps.androidgpt.presentation.chat_screen.ChatCompletion
import com.ps.androidgpt.presentation.chat_screen.ChatRequest
import retrofit2.Response
import javax.inject.Inject

class GptRepositoryImpl @Inject constructor(private val chatGPTService: ChatGPTService) : GptRepository{
        override suspend fun getChatCompletion(request: ChatRequest): Response<ChatCompletion> {
            return chatGPTService.getChatCompletion(request)
        }
    }