package com.ailurusrp.panda_todo.features.home.data.model

import io.realm.kotlin.types.RealmObject
import java.time.Instant

class TaskWithDeadline : Task, RealmObject {
    override lateinit var UUID: String
    override var name: String = ""
    override var creationDate: Long? = null
    override var completed: Boolean = false
    var deadlineDate: Long? = null
}