package com.ps.androidgpt.data.repository

import android.util.Log
import com.ps.androidgpt.data.local.entity.ChatEntryEntity
import com.ps.androidgpt.data.remote.ChatApi
import com.ps.androidgpt.data.remote.dto.ChatCompletion
import com.ps.androidgpt.data.remote.dto.ChatRequestDto
import com.ps.androidgpt.domain.repository.ChatRepository
import com.ps.androidgpt.utils.TAG
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatApi: ChatApi,
    private val realm: Realm
) : ChatRepository {
    override suspend fun getChatCompletion(
        apiKey: String,
        request: ChatRequestDto
    ): ChatCompletion {
        return chatApi.getChatCompletion(authorization = " Bearer $apiKey", request = request)
    }

    override fun getSavedData(): Flow<List<ChatEntryEntity>> {
        return realm.query<ChatEntryEntity>().asFlow().map { it.list }
    }

    override suspend fun insertChatEntry(chatEntryEntity: ChatEntryEntity) {
        realm.write { copyToRealm(chatEntryEntity) }
    }

    override suspend fun deleteChatEntry(id: ObjectId) {
        realm.write {
            val chatEntryEntity = query<ChatEntryEntity>(query = "id == $0", id).first().find()
            try {
                chatEntryEntity?.let {
                    delete(it)
                }
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, "Error: ${e.message}")
            }
        }
    }
}