package com.ps.androidgpt.data.local.entity

import com.ps.androidgpt.domain.model.PromptEntry
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class PromptEntity : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var prompt: String = ""
}

fun PromptEntity.toPromptEntry(): PromptEntry {
    return PromptEntry(id = this.id.toHexString(), prompt = this.prompt)
}
