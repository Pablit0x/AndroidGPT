package com.ps.androidgpt.domain.repository

import com.ps.androidgpt.data.local.entity.ChatEntryEntity
import com.ps.androidgpt.data.remote.dto.ChatCompletion
import com.ps.androidgpt.data.remote.dto.ChatRequestDto
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface ChatRepository {
    suspend fun getChatCompletion(apiKey: String, request: ChatRequestDto): ChatCompletion
    fun getSavedData(): Flow<List<ChatEntryEntity>>
    suspend fun insertChatEntry(chatEntryEntity: ChatEntryEntity)
    suspend fun deleteChatEntry(id: ObjectId)
}