package com.ps.androidgpt.domain.repository

import com.ps.androidgpt.data.local.entity.PromptEntity
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface PromptRepository {

    fun getSavedPrompts() : Flow<List<PromptEntity>>
    suspend fun insertPrompt(promptEntity: PromptEntity)
    suspend fun deletePrompt(id: ObjectId)
    suspend fun updatePrompt(promptEntity: PromptEntity)
}