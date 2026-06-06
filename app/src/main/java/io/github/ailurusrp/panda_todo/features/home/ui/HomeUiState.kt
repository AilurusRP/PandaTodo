package io.github.ailurusrp.panda_todo.features.home.ui

import io.github.ailurusrp.panda_todo.features.home.data.model.BasicTask
import io.github.ailurusrp.panda_todo.features.home.data.model.RecurringTask
import io.github.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadline
import io.github.ailurusrp.panda_todo.features.home.ui.addtaskdialog.DialogStatus

data class HomeUiState(
    val isRecurringTaskLoading: Boolean = false,

    val recurringTaskData: List<RecurringTask> = emptyList(),
    val taskWithDeadlineData: List<TaskWithDeadline> = emptyList(),
    val basicTaskData: List<BasicTask> = emptyList(),

    val isDrawerOpen: Boolean = false,
    val dialogStatus: DialogStatus? = null,
    val selectedMenuOptions: FilterMenuOptions = FilterMenuOptions.OpenTasks
)