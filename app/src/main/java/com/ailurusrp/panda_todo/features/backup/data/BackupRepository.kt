package com.ailurusrp.panda_todo.features.backup.data

import com.ailurusrp.panda_todo.features.home.data.model.toBasicTaskRealm
import com.ailurusrp.panda_todo.features.home.data.model.toRecurringTaskRealm
import com.ailurusrp.panda_todo.features.home.data.model.toTaskWithDeadlineRealm
import com.ailurusrp.panda_todo.features.home.data.repository.TaskRepository
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class BackupRepository(
    private val taskRepository: TaskRepository
) {
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

    suspend fun importBackup(inputStream: InputStream) {
        withContext(Dispatchers.IO) {
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val backupData = json.decodeFromString<BackupData>(jsonString)

            backupData.recurringTasks.filter { task ->
                !taskRepository.checkRecurringTaskExistenceById(
                    RealmUUID.from(task.id)
                )
            }.forEach { task ->
                taskRepository.addRecurringTask(task.toRecurringTaskRealm())
            }

            backupData.tasksWithDeadline.filter { task ->
                !taskRepository.checkTaskWithDeadlineExistenceById(
                    RealmUUID.from(task.id)
                )
            }.forEach { task ->
                taskRepository.addTaskWithDeadline(task.toTaskWithDeadlineRealm())
            }

            backupData.basicTasks.filter { task ->
                !taskRepository.checkBasicTaskExistenceById(
                    RealmUUID.from(task.id)
                )
            }.forEach { task ->
                taskRepository.addBasicTask(task.toBasicTaskRealm())
            }
        }
    }
}