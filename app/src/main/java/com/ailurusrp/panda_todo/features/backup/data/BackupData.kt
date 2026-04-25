package com.ailurusrp.panda_todo.features.backup.data

import com.ailurusrp.panda_todo.features.home.data.model.BasicTask
import com.ailurusrp.panda_todo.features.home.data.model.RecurringTask
import com.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadline
import kotlinx.serialization.Serializable

@Serializable
data class BackupData(
    val schemaVersion: Int = 1,
    val exportTime: Long = System.currentTimeMillis(),
    val basicTasks: List<BasicTask> = emptyList(),
    val recurringTasks: List<RecurringTask> = emptyList(),
    val tasksWithDeadline: List<TaskWithDeadline> = emptyList()
)
