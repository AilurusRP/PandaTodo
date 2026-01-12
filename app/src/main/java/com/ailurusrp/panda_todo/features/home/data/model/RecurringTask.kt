package com.ailurusrp.panda_todo.features.home.data.model

import com.ailurusrp.panda_todo.features.home.domain.ResetInterval
import io.realm.kotlin.types.RealmObject


class RecurringTask : Task, RealmObject {
    override lateinit var UUID: String
    override var name: String = ""
    override var creationDate: Long? = null
    override var completed: Boolean = false
    var resetInterval: String = ResetInterval.OneDay.name
}