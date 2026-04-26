package io.github.ailurusrp.panda_todo.features.dailyreport.data.model

import io.realm.kotlin.ext.toRealmList
import kotlinx.serialization.Serializable

@Serializable
class DailyReport(
    var creationDate: String = "",
    var completedTasks: List<String> = emptyList()
) {

    companion object {
        @JvmStatic
        fun fromDailyReportRealm(data: DailyReportRealm): DailyReport {
            return DailyReport(
                data.creationDate,
                data.completedTasks
            )
        }
    }
}

fun DailyReport.toDailyReportRealm(): DailyReportRealm {
    return DailyReportRealm().apply {
        creationDate = this@toDailyReportRealm.creationDate
        completedTasks = this@toDailyReportRealm.completedTasks.toRealmList()
    }
}

