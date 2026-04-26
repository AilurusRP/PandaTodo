package com.ailurusrp.panda_todo.features.dailyreport.data

import com.ailurusrp.panda_todo.features.dailyreport.data.DailyReportRealm
import io.realm.kotlin.RealmConfiguration

val dailyReportDatabaseConfig = RealmConfiguration.Builder(
    setOf(DailyReportRealm::class)
).name("daily_report").build()