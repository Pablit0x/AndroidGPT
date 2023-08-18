package com.ps.androidgpt.domain.use_case.edit_prompt

import com.ps.androidgpt.data.local.entity.PromptEntity
import com.ps.androidgpt.domain.repository.PromptRepository

class EditPromptUseCase(private val promptRepository: PromptRepository) {
    suspend operator fun invoke(promptEntity: PromptEntity) {
        promptRepository.updatePrompt(promptEntity = promptEntity)
    }
}