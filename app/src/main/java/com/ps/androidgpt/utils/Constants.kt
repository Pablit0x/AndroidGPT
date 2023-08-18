package com.ps.androidgpt.utils

object Constants {

    val models: List<String> = listOf(
        "gpt-3.5-turbo",
        "gpt-3.5-turbo-16k",
        "gpt-3.5-turbo-0613",
        "gpt-3.5-turbo-16k-0613",
        "gpt-4",
        "gpt-4-0613",
        "gpt-4-32k",
        "gpt-4-32k-0613"
    )

    const val USER_SETTINGS_FILE = "user_settings.json"

    const val PROMPT_NAVIGATION_ARGUMENT = "prompt"
    const val UPSERT_PROMPT_NAVIGATION_ARGUMENT = "upsert_prompt"

    const val DEFAULT_MODEL_ID = "gpt-3.5-turbo"
    const val BASE_URL = "https://api.openai.com/"
    const val CHAT_ROLE = "user"
    const val INVALID_API_KEY = "INVALID_KEY"
}