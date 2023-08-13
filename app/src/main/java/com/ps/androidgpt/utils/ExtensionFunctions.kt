package com.ps.androidgpt.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.ps.androidgpt.domain.model.UserSettings
import com.ps.androidgpt.domain.serializers.UserSettingsSerializer

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

val Context.dataStore: DataStore<UserSettings> by dataStore(
    fileName = Constants.USER_SETTINGS_FILE,
    serializer = UserSettingsSerializer
)