package com.ps.androidgpt.domain.use_case.delete_entry

import com.ps.androidgpt.domain.repository.ChatRepository
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class DeleteEntryUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(id: String) {
        chatRepository.deleteChatEntry(id = ObjectId(hexString = id))
    }
}