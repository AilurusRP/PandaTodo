package com.ailurusrp.panda_todo.features.home.data.model

import io.realm.kotlin.types.RealmObject

class SubTaskRealm : RealmObject {
    var order: Int = 0
    var name: String = ""
    var completed: Boolean = false
}