package com.ailurusrp.panda_todo.features.home.data.model

import io.realm.kotlin.types.RealmUUID
import kotlinx.serialization.Serializable

@Serializable
class TaskWithDeadline(
    override var id: String,
    override var name: String,
    override var creationDate: Long,
    override var completed: Boolean,
    var deadlineDate: Long,
    override var completionDate: Long?,
    override var subTasks: List<SubTask>
) : Task {

    companion object {
        @JvmStatic
        fun fromTaskWithDeadlineRealm(data: TaskWithDeadlineRealm): TaskWithDeadline {
            return TaskWithDeadline(
                data.id.toString(),
                data.name,
                data.creationDate,
                data.completed,
                data.deadlineDate,
                data.completionDate,
                data.subTasks.map { SubTask.fromSubTaskRealm(it) }
            )
        }
    }
}

