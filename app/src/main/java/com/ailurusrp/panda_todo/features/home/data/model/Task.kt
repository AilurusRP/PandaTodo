package com.ailurusrp.panda_todo.features.home.data.model

import io.realm.kotlin.types.RealmUUID

interface Task {
    var id: RealmUUID
    var name: String
    var creationDate: Long
    var completed: Boolean
}