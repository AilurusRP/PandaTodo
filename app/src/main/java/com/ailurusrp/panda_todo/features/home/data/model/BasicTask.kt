package com.ailurusrp.panda_todo.features.home.data.model

import com.ailurusrp.panda_todo.common.utils.DateUtils
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmUUID
import kotlinx.serialization.Serializable

@Serializable
class BasicTask(
    override var id: String,
    override var name: String,
    override var creationDate: Long,
    override var completed: Boolean,
    override var completionDate: Long?,
    override var subTasks: List<SubTask>
) : Task {

    companion object {
        @JvmStatic
        fun fromBasicTaskRealm(data: BasicTaskRealm): BasicTask {
            return BasicTask(
                data.id.toString(),
                data.name,
                data.creationDate,
                data.completed,
                data.completionDate,
                data.subTasks.map { SubTask.fromSubTaskRealm(it) }
            )
        }
    }
}

fun BasicTask.toBasicTaskRealm(): BasicTaskRealm {
    return BasicTaskRealm().apply {
        id = RealmUUID.from(this@toBasicTaskRealm.id)
        name = this@toBasicTaskRealm.name
        creationDate = this@toBasicTaskRealm.creationDate
        completed = this@toBasicTaskRealm.completed
        completionDate = this@toBasicTaskRealm.creationDate
        subTasks =
            this@toBasicTaskRealm.subTasks.map { subTask -> subTask.toSubTaskRealm() }.toRealmList()
    }
}