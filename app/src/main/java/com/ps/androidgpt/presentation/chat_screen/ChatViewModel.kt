package com.ps.androidgpt.presentation.chat_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.androidgpt.data.remote.dto.ChatMessageDto
import com.ps.androidgpt.data.remote.dto.ChatRequestDto
import com.ps.androidgpt.domain.use_case.get_response.GetResponseUseCase
import com.ps.androidgpt.domain.use_case.save_entry.InsertChatEntryUseCase
import com.ps.androidgpt.domain.model.ChatEntry
import com.ps.androidgpt.domain.model.toChatEntryEntity
import com.ps.androidgpt.utils.Constants
import com.ps.androidgpt.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getResponseUseCase: GetResponseUseCase,
    private val insertChatEntryUseCase: InsertChatEntryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    fun getChatResponse(query: String) {
        val chatRequest = query.toChatRequest()
        getResponseUseCase(chatRequest).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            chatEntries = it.chatEntries + ChatEntry(
                                response = result.data.toString(),
                                query = query,
                                time = RealmInstant.now().toString()
                            ),
                            isLoading = false
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = result.message ?: "An unexpected error occurred",
                            isLoading = false
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun insertChatEntry(chatEntry: ChatEntry){
        val chatEntry = chatEntry.toChatEntryEntity()
        viewModelScope.launch {
            insertChatEntryUseCase.invoke(chatEntry)
        }
    }

    private fun String.toChatRequest(): ChatRequestDto {
        return ChatRequestDto(
            model = Constants.MODEL_ID,
            messages = listOf(ChatMessageDto(role = Constants.CHAT_ROLE, content = this))
        )
    }
}


