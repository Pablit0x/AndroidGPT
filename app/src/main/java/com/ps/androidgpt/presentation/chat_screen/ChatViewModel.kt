package com.ps.androidgpt.presentation.chat_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.androidgpt.data.remote.dto.ChatMessageDto
import com.ps.androidgpt.data.remote.dto.ChatRequestDto
import com.ps.androidgpt.domain.use_case.get_response.GetResponseUseCase
import com.ps.androidgpt.utils.Constants
import com.ps.androidgpt.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getResponseUseCase: GetResponseUseCase
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
                            response = it.response + result.data.toString(),
                            queries = it.queries + query,
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

    private fun String.toChatRequest(): ChatRequestDto {
        return ChatRequestDto(
            model = Constants.MODEL_ID,
            messages = listOf(ChatMessageDto(role = Constants.CHAT_ROLE, content = this))
        )
    }
}


