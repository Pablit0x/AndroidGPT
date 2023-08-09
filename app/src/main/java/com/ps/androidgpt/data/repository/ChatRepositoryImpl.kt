package com.ps.androidgpt.data.repository

import com.ps.androidgpt.data.ChatApi
import com.ps.androidgpt.domain.model.ChatCompletion
import com.ps.androidgpt.domain.model.ChatRequest
import com.ps.androidgpt.domain.repository.ChatRepository
import retrofit2.Response
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(private val chatApi: ChatApi) : ChatRepository{
        override suspend fun getChatCompletion(request: ChatRequest): Response<ChatCompletion> {
            return chatApi.getChatCompletion(request = request)
        }
    }