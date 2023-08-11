package com.ps.androidgpt.data.repository

import android.util.Log
import com.ps.androidgpt.data.remote.ChatApi
import com.ps.androidgpt.data.remote.dto.ChatCompletion
import com.ps.androidgpt.data.remote.dto.ChatRequestDto
import com.ps.androidgpt.domain.model.ChatEntry
import com.ps.androidgpt.domain.repository.ChatRepository
import com.ps.androidgpt.utils.TAG
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatApi: ChatApi, private val realm: Realm
) : ChatRepository {
    override suspend fun getChatCompletion(request: ChatRequestDto): ChatCompletion {
        return chatApi.getChatCompletion(request = request)
    }

    override fun getSavedData(): Flow<List<ChatEntry>> {
        return realm.query<ChatEntry>().asFlow().map { it.list }
    }

    override fun filterData(name: String): Flow<List<ChatEntry>> {
        return realm.query<ChatEntry>(query = "name CONTAINS[c] $0").asFlow().map { it.list }
    }

    override suspend fun insertChatEntry(chatEntry: ChatEntry) {
        realm.write { copyToRealm(chatEntry) }
    }

    override suspend fun deleteChatEntry(id: ObjectId) {
        realm.write {
            val chatEntry = query<ChatEntry>(query = "id == $0", id).first().find()
            try {
                chatEntry?.let {
                    delete(it)
                }
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, "Error: ${e.message}")
            }
        }
    }
}