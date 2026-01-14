package com.ailurusrp.panda_todo.features.home.data.database

import com.ailurusrp.panda_todo.features.home.data.model.BasicTaskRealm
import com.ailurusrp.panda_todo.features.home.data.model.RecurringTaskRealm
import com.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadlineRealm
import io.realm.kotlin.RealmConfiguration

val homeDatabaseConfig = RealmConfiguration.create(
    setOf(
        BasicTaskRealm::class,
        RecurringTaskRealm::class,
        TaskWithDeadlineRealm::class
    )
)