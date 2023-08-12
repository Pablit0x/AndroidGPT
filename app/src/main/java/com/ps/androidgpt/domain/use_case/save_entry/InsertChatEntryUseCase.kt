package com.ps.androidgpt.domain.use_case.save_entry

import com.ps.androidgpt.data.local.entity.ChatEntryEntity
import com.ps.androidgpt.domain.repository.ChatRepository
import javax.inject.Inject

class InsertChatEntryUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatEntryEntity: ChatEntryEntity){
        chatRepository.insertChatEntry(chatEntryEntity = chatEntryEntity)
    }
}