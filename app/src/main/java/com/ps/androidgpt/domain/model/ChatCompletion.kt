package com.ps.androidgpt.domain.model

import com.google.gson.annotations.SerializedName

data class ChatCompletion(
    val id: String,
    @SerializedName("object") val apiObject: String,
    val created: Long,
    val model: String,
    val choices: List<ChatChoice>
)
