package com.ailurusrp.panda_todo.features.backup.data

import com.ailurusrp.panda_todo.features.home.data.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.OutputStream

class BackupRepository (
    private val taskRepository: TaskRepository
){
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun exportBackup(outputStream: OutputStream) {
        withContext(Dispatchers.IO) {
            val basicTasks = taskRepository.getBasicTasks().first()
            val recurringTasks = taskRepository.getRecurringTasks().first()
            val tasksWithDeadline = taskRepository.getTasksWithDeadlines().first()

            val backupData = BackupData(
                basicTasks = basicTasks,
                recurringTasks = recurringTasks,
                tasksWithDeadline = tasksWithDeadline
            )

            val jsonString = json.encodeToString(backupData)
            outputStream.write(jsonString.toByteArray())
            outputStream.close()
        }
    }
}