package com.ps.androidgpt.domain.use_case.save_entry

import com.ps.androidgpt.domain.model.ChatEntry
import com.ps.androidgpt.domain.repository.ChatRepository
import javax.inject.Inject

class InsertChatEntryUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatEntry: ChatEntry){
        chatRepository.insertChatEntry(chatEntry = chatEntry)
    }
}