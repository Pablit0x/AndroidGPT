package com.ps.androidgpt.domain.use_case.get_saved_entries

import android.util.Log
import androidx.compose.runtime.collectAsState
import com.ps.androidgpt.data.local.entity.toChatEntry
import com.ps.androidgpt.data.remote.dto.ChatRequestDto
import com.ps.androidgpt.data.remote.dto.toStringResponse
import com.ps.androidgpt.domain.model.ChatEntry
import com.ps.androidgpt.domain.repository.ChatRepository
import com.ps.androidgpt.utils.Resource
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
//        Log.d("lolipop", "called here???")
//        try {
//            emit(Resource.Loading())
//            val chatEntries = chatRepository.getSavedData().map { chatEntryEntities ->
//                chatEntryEntities.map { chatEntryEntity ->
//                    chatEntryEntity.toChatEntry()
//                }
//            }
//
//            chatEntries.collect{
//                it.forEach{
//                    Log.d("lolipop", " WTFFF !!! $it!")
//                }
//
//            }
//
//            chatEntries.map {
//                emit(Resource.Success(data = it))
//            }
//
//        } catch (e: HttpException) {
//            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
//        }
//        catch (e: IOException) {
//            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
//        }
//    }