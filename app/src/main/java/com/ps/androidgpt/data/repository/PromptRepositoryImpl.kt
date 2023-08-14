package com.ps.androidgpt.data.repository

import android.util.Log
import com.ps.androidgpt.data.local.entity.ChatEntryEntity
import com.ps.androidgpt.data.local.entity.PromptEntity
import com.ps.androidgpt.domain.repository.PromptRepository
import com.ps.androidgpt.utils.TAG
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class PromptRepositoryImpl @Inject constructor(
    private val realm: Realm
) : PromptRepository {
    override fun getSavedPrompts(): Flow<List<PromptEntity>> {
        return realm.query<PromptEntity>().asFlow().map { it.list }
    }

    override suspend fun insertPrompt(promptEntity: PromptEntity) {
        realm.write { copyToRealm(promptEntity) }
    }

    override suspend fun deletePrompt(id: ObjectId) {
        realm.write {
            val promptEntity = query<PromptEntity>(query = "id == $0", id).first().find()
            try {
                promptEntity?.let {
                    delete(it)
                }
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, "Error: ${e.message}")
            }
        }
    }

    override suspend fun updatePrompt(promptEntity: PromptEntity) {
        realm.write {
            val prompt = query<PromptEntity>(query = "id == $0", promptEntity.id).first().find()
            prompt?.prompt = promptEntity.prompt
        }
    }
}