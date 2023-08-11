package com.ps.androidgpt.data.repository

import com.ps.androidgpt.data.remote.ChatApi
import com.ps.androidgpt.data.remote.dto.ChatCompletion
import com.ps.androidgpt.data.remote.dto.ChatRequestDto
import com.ps.androidgpt.domain.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(private val chatApi: ChatApi) : ChatRepository{
        override suspend fun getChatCompletion(request: ChatRequestDto): ChatCompletion {
            return chatApi.getChatCompletion(request = request)
        }
    }