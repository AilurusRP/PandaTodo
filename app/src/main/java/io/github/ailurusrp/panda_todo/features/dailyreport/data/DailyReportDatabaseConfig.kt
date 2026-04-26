package io.github.ailurusrp.panda_todo.features.dailyreport.data

import io.github.ailurusrp.panda_todo.features.dailyreport.data.model.DailyReportRealm
import io.realm.kotlin.RealmConfiguration

val dailyReportDatabaseConfig = RealmConfiguration.Builder(
    setOf(DailyReportRealm::class)
).name("daily_report").build()