package com.ps.androidgpt.domain.use_case.get_saved_entries

import com.ps.androidgpt.data.local.entity.toChatEntry
import com.ps.androidgpt.domain.model.ChatEntry
import com.ps.androidgpt.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSavedEntriesUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    operator fun invoke(): Flow<List<ChatEntry>> {
        return chatRepository.getSavedData().map { chatEntryEntities ->
            chatEntryEntities.map {
                it.toChatEntry()
            }
        }
    }
}