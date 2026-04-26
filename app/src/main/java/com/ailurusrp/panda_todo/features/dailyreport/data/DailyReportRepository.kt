package com.ailurusrp.panda_todo.features.dailyreport.data

import com.ailurusrp.panda_todo.features.dailyreport.data.model.DailyReport
import com.ailurusrp.panda_todo.features.dailyreport.data.model.DailyReportRealm
import com.ailurusrp.panda_todo.features.dailyreport.exceptions.MoreThanOneDailyReportFoundException
import com.ailurusrp.panda_todo.features.dailyreport.exceptions.NoDailyReportFoundException
import com.ailurusrp.panda_todo.features.home.data.model.RecurringTaskRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class DailyReportRepository(private val realm: Realm) {
    fun getAllDailyReports(): List<DailyReport> {
        return realm.query<DailyReportRealm>().find().toList()
            .map { dailyReportRealm -> DailyReport.fromDailyReportRealm(dailyReportRealm) }
    }

    fun checkDailyReportExistenceByDate(date: String): Boolean {
        return realm.query<DailyReportRealm>("creationDate == $0", date).first().find() != null
    }

    suspend fun addDailyReport(dailyReport: DailyReportRealm) {
        realm.write { copyToRealm(dailyReport) }
    }

    suspend fun addNewCompletedTaskToDailyReport(date: String, newCompletedTask: String) {
        realm.write {
            val dailyReport = this.query<DailyReportRealm>("creationDate == $0", date).find()
            if (dailyReport.size > 1) throw MoreThanOneDailyReportFoundException()
            dailyReport.firstOrNull()?.completedTasks?.add(newCompletedTask)
                ?: throw NoDailyReportFoundException()
        }
    }

    suspend fun deleteCompletedTaskFromDailyReport(date: String, newCompletedTask: String) {
        realm.write {
            val dailyReport = this.query<DailyReportRealm>("creationDate == $0", date).find()
            if (dailyReport.size > 1) throw MoreThanOneDailyReportFoundException()
            dailyReport.firstOrNull()?.completedTasks?.removeAll { it == newCompletedTask }
                ?: throw NoDailyReportFoundException()
        }
    }
}