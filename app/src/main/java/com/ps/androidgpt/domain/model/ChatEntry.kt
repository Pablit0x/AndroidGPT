package com.ps.androidgpt.domain.model

import com.ps.androidgpt.data.local.entity.ChatEntryEntity

data class ChatEntry(
    val id: String? = null, val response: String, val query: String, val time: String
)

fun ChatEntry.toChatEntryEntity(): ChatEntryEntity {
    val chatEntryEntity = ChatEntryEntity()
    chatEntryEntity.response = response
    chatEntryEntity.query = query
    return chatEntryEntity
}

fun ChatEntry.matchesSearch(searchQuery: String): Boolean {
    val normalizedQuery = searchQuery.lowercase()

    return response.lowercase().contains(normalizedQuery) || query.lowercase()
        .contains(normalizedQuery)
}