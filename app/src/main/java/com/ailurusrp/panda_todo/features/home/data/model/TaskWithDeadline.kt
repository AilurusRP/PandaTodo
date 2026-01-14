package com.ailurusrp.panda_todo.features.home.data.model

import io.realm.kotlin.types.RealmUUID

class TaskWithDeadline(
    override var id: RealmUUID,
    override var name: String,
    override var creationDate: Long?,
    override var completed: Boolean,
    var deadlineDate: Long?
) : Task {
    companion object {
        @JvmStatic
        fun fromTaskWithDeadlineRealm(data: TaskWithDeadlineRealm): TaskWithDeadline {
            return TaskWithDeadline(
                data.id,
                data.name,
                data.creationDate,
                data.completed,
                data.deadlineDate
            )
        }
    }
}

