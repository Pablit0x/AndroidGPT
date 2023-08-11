package com.ps.androidgpt.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ChatCompletion(
    val id: String,
    @SerializedName("object") val apiObject: String,
    val created: Long,
    val model: String,
    val choices: List<ChatChoiceDto>
)

fun ChatCompletion.toStringResponse() : String {
    return choices.first().message.content
}
