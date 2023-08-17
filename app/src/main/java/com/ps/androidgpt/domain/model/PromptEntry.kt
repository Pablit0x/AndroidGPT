package com.ps.androidgpt.domain.model

import com.ps.androidgpt.data.local.entity.PromptEntity

data class PromptEntry(
    val id: String? = null,
    val prompt: String
)

fun PromptEntry.toPromptEntity(): PromptEntity {
    val promptEntity = PromptEntity()
    promptEntity.prompt = this.prompt
    return promptEntity
}
