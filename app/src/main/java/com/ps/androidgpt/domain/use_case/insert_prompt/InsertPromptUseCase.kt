package com.ps.androidgpt.domain.use_case.insert_prompt

import com.ps.androidgpt.data.local.entity.PromptEntity
import com.ps.androidgpt.domain.repository.PromptRepository
import javax.inject.Inject

class InsertPromptUseCase @Inject constructor(
    private val promptRepository: PromptRepository
) {
    suspend operator fun invoke(promptEntity: PromptEntity) {
        promptRepository.insertPrompt(promptEntity = promptEntity)
    }
}