package com.ps.androidgpt.data.local.entity

import com.ps.androidgpt.domain.model.ChatEntry
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ChatEntryEntity : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var response: String = ""
    var query: String = ""
    var timestamp: RealmInstant = RealmInstant.now()
}

fun ChatEntryEntity.toChatEntry() : ChatEntry {
    return ChatEntry(
        id = id.toHexString(),
        response = response,
        query = query,
        time = timestamp.toDateTime()
    )
}

fun RealmInstant.toDateTime(): String {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this.epochSeconds * 1000)

    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.get(Calendar.MONTH) + 1
    val year = calendar.get(Calendar.YEAR) % 100
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    return String.format(
        Locale.getDefault(),
        "%02d/%02d/%02d %02d:%02d",
        day,
        month,
        year,
        hour,
        minute
    )
}
