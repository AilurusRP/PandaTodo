package com.ailurusrp.panda_todo.features.home.data.model

import io.realm.kotlin.types.RealmUUID

class BasicTask(
    override var id: RealmUUID, override var name: String,
    override var creationDate: Long, override var completed: Boolean
) : Task {
    companion object {
        @JvmStatic
        fun fromBasicTaskRealm(data: BasicTaskRealm): BasicTask {
            return BasicTask(data.id, data.name, data.creationDate, data.completed)
        }
    }
}