package com.ailurusrp.panda_todo.features.home.data.model

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