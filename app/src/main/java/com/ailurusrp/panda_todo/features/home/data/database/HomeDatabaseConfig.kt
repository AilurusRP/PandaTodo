package com.ailurusrp.panda_todo.features.home.data.database

import com.ailurusrp.panda_todo.features.home.data.model.BasicTask
import com.ailurusrp.panda_todo.features.home.data.model.RecurringTask
import com.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadline
import io.realm.kotlin.RealmConfiguration

val homeDatabaseConfig = RealmConfiguration.create(
    setOf(
        BasicTask::class,
        RecurringTask::class,
        TaskWithDeadline::class
    )
)