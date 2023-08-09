package com.ps.androidgpt.domain.model

import com.google.gson.annotations.SerializedName

data class ChatChoice(
    val message: ChatMessage,
    @SerializedName("finish_reason") val finishReason: String,
    val index: Int
)
