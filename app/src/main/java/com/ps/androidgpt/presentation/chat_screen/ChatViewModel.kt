package com.ps.androidgpt.presentation.chat_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.ps.androidgpt.domain.repository.GptRepository
import com.ps.androidgpt.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: GptRepository
) : ViewModel() {

    private val _state = MutableStateFlow("")
    val state = _state.asStateFlow()

//    val request = ChatGPTRequest(
//        messages = listOf(
//            Message(role = "system", content = "You are a helpful assistant."),
//            Message(role = "user", content = "Tell me a joke.")
//        )
//    )

    fun getChatGPTResponse() {

        viewModelScope.launch {
            val chatRequest = ChatRequest(
                model = "gpt-3.5-turbo",
                messages = listOf(ChatMessage(role = "user", content = "Say this is a test!"))
            )

            try {
                val response = chatRepository.getChatCompletion(chatRequest)
                if (response.isSuccessful) {
                    val chatResponse = response.body()
                    Log.d("lolipop", "response + $chatResponse")
                    // Handle the response
                } else {
                    Log.e("lolipop", "response + ${response.errorBody()?.string()}")

                    // Handle error
                }
            } catch (e: Exception) {
                Log.e("lolipop", "error + ${e.message}")
                // Handle exception
            }
        }
    }
}


data class ChatChoice(
    val message: ChatMessage,
    @SerializedName("finish_reason") val finishReason: String,
    val index: Int
)

data class ChatCompletion(
    val id: String,
    @SerializedName("object") val apiObject: String,
    val created: Long,
    val model: Model,
    val choices: List<ChatChoice>
)

data class Model(
    val id: String
)


data class ChatMessage(
    val role: String,
    val content: String
)

data class ChatRequest(
    val model: String,
    val messages: List<ChatMessage>
)