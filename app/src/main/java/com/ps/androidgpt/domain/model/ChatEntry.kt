package com.ps.androidgpt.domain.model

import com.ps.androidgpt.presentation.model.ChatEntryUI
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class ChatEntry : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var response: String = ""
    var query: String = ""
    var timestamp: RealmInstant = RealmInstant.now()
}

fun ChatEntry.toChatEntryUI() : ChatEntryUI{
    return ChatEntryUI(
        response = response,
        query = query,
        time = timestamp.toString()
    )
}
