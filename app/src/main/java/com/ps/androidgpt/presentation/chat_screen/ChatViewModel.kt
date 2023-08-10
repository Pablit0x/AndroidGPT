package com.ps.androidgpt.presentation.chat_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.androidgpt.domain.model.ChatMessage
import com.ps.androidgpt.domain.model.ChatRequest
import com.ps.androidgpt.domain.repository.ChatRepository
import com.ps.androidgpt.utils.Constants
import com.ps.androidgpt.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()
    fun getChatResponse(query: String) {

        _state.update {
            it.copy(
                queries = it.queries + query,
                isLoading = true
            )
        }

        viewModelScope.launch {
            val chatRequest = ChatRequest(
                model = Constants.MODEL_ID,
                messages = listOf(ChatMessage(role = Constants.CHAT_ROLE, content = query))
            )
            try {
                val response = chatRepository.getChatCompletion(chatRequest)
                if (response.isSuccessful) {
                    val chatResponse = response.body()?.choices?.first()
                    Log.d(TAG, " body = ${chatResponse?.message?.content}")
                    if(chatResponse != null){
                        _state.update {
                            it.copy(
                                response = it.response + chatResponse.message.content
                            )
                        }
                    }
                } else {
                    Log.e(TAG, "error body = ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "exception = ${e.message}")
            } finally {
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }
}

