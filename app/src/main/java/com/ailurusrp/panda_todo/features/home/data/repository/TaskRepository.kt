package com.ailurusrp.panda_todo.features.home.data.repository

import com.ailurusrp.panda_todo.features.home.data.model.BasicTask
import com.ailurusrp.panda_todo.features.home.data.model.BasicTaskRealm
import com.ailurusrp.panda_todo.features.home.data.model.RecurringTask
import com.ailurusrp.panda_todo.features.home.data.model.RecurringTaskRealm
import com.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadline
import com.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadlineRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(
    private val realm: Realm
) {
    fun getRecurringTasks(): Flow<List<RecurringTask>> {
        return realm.query<RecurringTaskRealm>().asFlow()
            .map { results -> results.list.map { RecurringTask.fromRecurringTaskRealm(it) } }
    }

    fun getTasksWithDeadlines(): Flow<List<TaskWithDeadline>> {
        return realm.query<TaskWithDeadlineRealm>().asFlow()
            .map { results -> results.list.map { TaskWithDeadline.fromTaskWithDeadlineRealm(it) } }
    }

    fun getBasicTasks(): Flow<List<BasicTask>> {
        return realm.query<BasicTaskRealm>().asFlow()
            .map { results -> results.list.map { BasicTask.fromBasicTaskRealm(it) } }
    }

    fun checkRecurringTaskExistenceById(id: RealmUUID): Boolean {
        return realm.query<RecurringTaskRealm>("id == $0", id).first().find() != null
    }

    fun checkTaskWithDeadlineExistenceById(id: RealmUUID): Boolean {
        return realm.query<TaskWithDeadlineRealm>("id == $0", id).first().find() != null
    }

    fun checkBasicTaskExistenceById(id: RealmUUID): Boolean {
        return realm.query<BasicTaskRealm>("id == $0", id).first().find() != null
    }

    suspend fun addRecurringTask(task: RecurringTaskRealm) {
        realm.write { copyToRealm(task) }
    }

    suspend fun addTaskWithDeadline(task: TaskWithDeadlineRealm) {
        realm.write { copyToRealm(task) }
    }

    suspend fun addBasicTask(task: BasicTaskRealm) {
        realm.write { copyToRealm(task) }
    }

    private suspend inline fun <reified T : RealmObject> deleteById(id: RealmUUID) {
        realm.write {
            query<T>("id == $0", id).first().find()?.let { delete(it) }
        }
    }

    suspend fun deleteRecurringTask(id: RealmUUID) = deleteById<RecurringTaskRealm>(id)
    suspend fun deleteTaskWithDeadline(id: RealmUUID) = deleteById<TaskWithDeadlineRealm>(id)
    suspend fun deleteBasicTask(id: RealmUUID) = deleteById<BasicTaskRealm>(id)


    private suspend inline fun <reified T : RealmObject> updateById(
        id: RealmUUID,
        crossinline update: T.() -> Unit
    ) {
        realm.write {
            query<T>("id == $0", id).first().find()?.update()
        }
    }

    suspend fun updateRecurringTaskCompletionState(id: RealmUUID, newState: Boolean) =
        updateById<RecurringTaskRealm>(id) { completed = newState }

    suspend fun updateTaskWithDeadlineCompletionState(id: RealmUUID, newState: Boolean) =
        updateById<TaskWithDeadlineRealm>(id) { completed = newState }

    suspend fun updateBasicTaskCompletionState(id: RealmUUID, newState: Boolean) =
        updateById<BasicTaskRealm>(id) { completed = newState }

    suspend fun updateRecurringTaskCompletionDate(id: RealmUUID, newDate: Long?) =
        updateById<RecurringTaskRealm>(id) { completionDate = newDate }

    suspend fun updateTaskWithDeadlineCompletionDate(id: RealmUUID, newDate: Long?) =
        updateById<TaskWithDeadlineRealm>(id) { completionDate = newDate }

    suspend fun updateBasicTaskCompletionDate(id: RealmUUID, newDate: Long?) =
        updateById<BasicTaskRealm>(id) { completionDate = newDate }
}
