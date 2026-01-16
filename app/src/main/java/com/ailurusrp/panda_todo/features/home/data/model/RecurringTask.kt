package com.ailurusrp.panda_todo.features.home.data.model

import com.ailurusrp.panda_todo.features.home.domain.ResetInterval
import io.realm.kotlin.types.RealmUUID

class RecurringTask(
    override var id: RealmUUID,
    override var name: String,
    override var creationDate: Long,
    override var completed: Boolean,
    var resetInterval: String
) : Task {
    companion object {
        @JvmStatic
        fun fromRecurringTaskRealm(data: RecurringTaskRealm): RecurringTask {
            return RecurringTask(
                data.id,
                data.name,
                data.creationDate,
                data.completed,
                data.resetInterval
            )
        }
    }
}

