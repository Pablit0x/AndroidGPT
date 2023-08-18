package com.ps.androidgpt.domain.use_case.delete_prompt

import com.ps.androidgpt.domain.repository.PromptRepository
import org.mongodb.kbson.BsonObjectId

class DeletePromptUseCase(private val promptRepository: PromptRepository) {
    suspend operator fun invoke(id: String) {
        promptRepository.deletePrompt(id = BsonObjectId(hexString = id))
    }
}