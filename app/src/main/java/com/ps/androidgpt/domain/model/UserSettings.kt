package com.ps.androidgpt.domain.model

import com.ps.androidgpt.utils.Constants
import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val model: String = Constants.DEFAULT_MODEL_ID,
    val apiKey: String = Constants.INVALID_API_KEY,
)