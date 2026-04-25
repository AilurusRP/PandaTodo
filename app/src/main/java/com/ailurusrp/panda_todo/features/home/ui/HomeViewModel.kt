package com.ailurusrp.panda_todo.features.home.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ailurusrp.panda_todo.common.ui.HomeViews
import com.ailurusrp.panda_todo.features.home.data.database.homeDatabaseConfig
import com.ailurusrp.panda_todo.features.home.data.model.BasicTaskRealm
import com.ailurusrp.panda_todo.features.home.data.model.RecurringTaskRealm
import com.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadlineRealm
import com.ailurusrp.panda_todo.features.home.data.repository.TaskRepository
import com.ailurusrp.panda_todo.features.home.ui.addtaskdialog.DialogStatus
import io.realm.kotlin.Realm
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TaskRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            launch {
                repository.getRecurringTasks()
                    .catch { err ->
                        err.message?.let { Log.e("LoadTaskError", it) }
                    }
                    .collect { tasks ->
                        _uiState.update { it.copy(recurringTaskData = tasks) }
                    }
            }

            launch {
                repository.getTasksWithDeadlines()
                    .catch { err ->
                        err.message?.let { Log.e("LoadTaskError", it) }
                    }
                    .collect { tasks ->
                        _uiState.update { it.copy(taskWithDeadlineData = tasks) }
                    }
            }
            launch {
                repository.getBasicTasks()
                    .catch { err ->
                        err.message?.let { Log.e("LoadTaskError", it) }
                    }
                    .collect { tasks ->
                        _uiState.update { it.copy(basicTaskData = tasks) }
                    }
            }
        }
    }

    fun addRecurringTask(task: RecurringTaskRealm) {
        viewModelScope.launch {
            runCatching { repository.addRecurringTask(task) }
                .onFailure { err -> err.message?.let { Log.e("AddTaskError", it) } }
        }
    }

    fun addTaskWithDeadline(task: TaskWithDeadlineRealm) {
        viewModelScope.launch {
            runCatching { repository.addTaskWithDeadline(task) }
                .onFailure { err -> err.message?.let { Log.e("AddTaskError", it) } }
        }
    }

    fun addBasicTask(task: BasicTaskRealm) {
        viewModelScope.launch {
            runCatching { repository.addBasicTask(task) }
                .onFailure { err -> err.message?.let { Log.e("AddTaskError", it) } }
        }
    }

    fun deleteRecurringTask(id: RealmUUID) {
        viewModelScope.launch {
            runCatching { repository.deleteRecurringTask(id) }
                .onFailure { err -> err.message?.let { Log.e("DeleteTaskError", it) } }
        }
    }

    fun deleteTaskWithDeadline(id: RealmUUID) {
        viewModelScope.launch {
            runCatching { repository.deleteTaskWithDeadline(id) }
                .onFailure { err -> err.message?.let { Log.e("DeleteTaskError", it) } }
        }
    }

    fun deleteBasicTask(id: RealmUUID) {
        viewModelScope.launch {
            runCatching { repository.deleteBasicTask(id) }
                .onFailure { err -> err.message?.let { Log.e("DeleteTaskError", it) } }
        }
    }

    fun updateRecurringTaskCompletionState(id: RealmUUID, newState: Boolean) {
        viewModelScope.launch {
            repository.updateRecurringTaskCompletionState(id, newState)
        }
    }

    fun updateTaskWithDeadlineCompletionState(id: RealmUUID, newState: Boolean) {
        viewModelScope.launch {
            repository.updateTaskWithDeadlineCompletionState(id, newState)
        }
    }

    fun updateBasicTaskCompletionState(id: RealmUUID, newState: Boolean) {
        viewModelScope.launch {
            repository.updateBasicTaskCompletionState(id, newState)
        }
    }

    fun updateRecurringTaskCompletionDate(id: RealmUUID, newDate: Long?) {
        viewModelScope.launch {
            repository.updateRecurringTaskCompletionDate(id, newDate)
        }
    }

    fun updateTaskWithDeadlineCompletionDate(id: RealmUUID, newDate: Long?) {
        viewModelScope.launch {
            repository.updateTaskWithDeadlineCompletionDate(id, newDate)
        }
    }

    fun updateBasicTaskCompletionDate(id: RealmUUID, newDate: Long?) {
        viewModelScope.launch {
            repository.updateBasicTaskCompletionDate(id, newDate)
        }
    }


    fun changeDrawerState(newState: Boolean) {
        _uiState.update { it.copy(isDrawerOpen = newState) }
    }

    fun changeDialogStatus(newStatus: DialogStatus?) {
        _uiState.update { it.copy(dialogStatus = newStatus) }
    }

    fun changeSelectedView(newView: HomeViews) {
        _uiState.update { it.copy(selectedView = newView) }
    }

    fun changeSelectedFilterOption(newFilterOption: FilterMenuOptions) {
        _uiState.update { it.copy(selectedMenuOptions = newFilterOption) }
    }
}

class HomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val realm = Realm.open(homeDatabaseConfig)
        val repository = TaskRepository(realm)
        @Suppress("UNCHECKED_CAST")
        return HomeViewModel(repository) as T
    }
}