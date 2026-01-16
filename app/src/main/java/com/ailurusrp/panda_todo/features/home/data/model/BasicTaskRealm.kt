package com.ailurusrp.panda_todo.features.home.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey

class BasicTaskRealm : TaskRealm, RealmObject {
    @PrimaryKey
    override var id: RealmUUID = RealmUUID.random()
    override var name: String = ""
    override var creationDate: Long = 0
    override var completed: Boolean = false
}