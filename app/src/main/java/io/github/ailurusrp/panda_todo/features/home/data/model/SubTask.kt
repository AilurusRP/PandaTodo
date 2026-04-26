package io.github.ailurusrp.panda_todo.features.home.data.model

import kotlinx.serialization.Serializable

@Serializable
class SubTask(var order: Int, var name: String, var completed: Boolean) {
    companion object {
        @JvmStatic
        fun fromSubTaskRealm(subTaskRealm: SubTaskRealm): SubTask {
            return SubTask(
                order = subTaskRealm.order,
                name = subTaskRealm.name,
                completed = subTaskRealm.completed
            )
        }
    }
}

fun SubTask.toSubTaskRealm(): SubTaskRealm {
    return SubTaskRealm().apply {
        order = this@toSubTaskRealm.order
        name = this@toSubTaskRealm.name
        completed = this@toSubTaskRealm.completed
    }
}