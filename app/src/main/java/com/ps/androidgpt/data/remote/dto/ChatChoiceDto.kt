package com.ps.androidgpt.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ChatChoiceDto(
    val message: ChatMessageDto,
    @SerializedName("finish_reason") val finishReason: String,
    val index: Int
)
