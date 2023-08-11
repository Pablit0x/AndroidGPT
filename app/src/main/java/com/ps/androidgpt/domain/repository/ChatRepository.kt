package com.ps.androidgpt.domain.repository

import com.ps.androidgpt.data.remote.dto.ChatCompletion
import com.ps.androidgpt.data.remote.dto.ChatRequestDto
import com.ps.androidgpt.domain.model.ChatEntry
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface ChatRepository {
    suspend fun getChatCompletion(request: ChatRequestDto): ChatCompletion
    fun getSavedData() : Flow<List<ChatEntry>>
    fun filterData(name: String) : Flow<List<ChatEntry>>

    suspend fun insertChatEntry(chatEntry: ChatEntry)

    suspend fun deleteChatEntry(id: ObjectId)
}