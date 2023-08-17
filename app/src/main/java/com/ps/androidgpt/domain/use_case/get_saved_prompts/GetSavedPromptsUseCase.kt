package com.ps.androidgpt.domain.use_case.get_saved_prompts

import com.ps.androidgpt.data.local.entity.toPromptEntry
import com.ps.androidgpt.domain.model.PromptEntry
import com.ps.androidgpt.domain.repository.PromptRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSavedPromptsUseCase @Inject constructor(private val promptRepository: PromptRepository) {
    operator fun invoke(): Flow<List<PromptEntry>> {
        return promptRepository.getSavedPrompts().map { prompts ->
            prompts.map {
                it.toPromptEntry()
            }
        }
    }
}