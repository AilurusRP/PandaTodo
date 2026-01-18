package com.ailurusrp.panda_todo.features.home.data.model

import com.ailurusrp.panda_todo.features.home.domain.ResetInterval
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey


class RecurringTaskRealm : TaskRealm, RealmObject {
    @PrimaryKey
    override var id: RealmUUID = RealmUUID.random()
    override var name: String = ""
    override var creationDate: Long = 0
    override var completed: Boolean = false
    override var completionDate: Long? = null
    var resetInterval: String = ResetInterval.OneDay.name
    override var subTasks: RealmList<SubTaskRealm> = realmListOf()
}