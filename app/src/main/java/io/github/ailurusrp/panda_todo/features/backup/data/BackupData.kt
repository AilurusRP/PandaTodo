package io.github.ailurusrp.panda_todo.features.backup.data

import io.github.ailurusrp.panda_todo.features.dailyreport.data.model.DailyReport
import io.github.ailurusrp.panda_todo.features.home.data.model.BasicTask
import io.github.ailurusrp.panda_todo.features.home.data.model.RecurringTask
import io.github.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadline
import kotlinx.serialization.Serializable

@Serializable
data class BackupData(
    val schemaVersion: Int = 2,
    val exportTime: Long = System.currentTimeMillis(),
    val basicTasks: List<BasicTask> = emptyList(),
    val recurringTasks: List<RecurringTask> = emptyList(),
    val tasksWithDeadline: List<TaskWithDeadline> = emptyList(),
    val dailyReports: List<DailyReport> = emptyList()
)
