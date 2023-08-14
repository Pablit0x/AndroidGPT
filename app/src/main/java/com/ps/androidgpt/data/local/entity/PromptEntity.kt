package com.ps.androidgpt.data.local.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class PromptEntity : RealmObject{
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var prompt: String = ""
}
