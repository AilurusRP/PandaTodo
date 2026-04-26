package io.github.ailurusrp.panda_todo.features.home.data.database

import io.github.ailurusrp.panda_todo.features.home.data.model.BasicTaskRealm
import io.github.ailurusrp.panda_todo.features.home.data.model.RecurringTaskRealm
import io.github.ailurusrp.panda_todo.features.home.data.model.SubTaskRealm
import io.github.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadlineRealm
import io.realm.kotlin.RealmConfiguration

val homeDatabaseConfig = RealmConfiguration.Builder(
    setOf(
        BasicTaskRealm::class,
        RecurringTaskRealm::class,
        TaskWithDeadlineRealm::class,
        SubTaskRealm::class
    )
).name("home_data").build()