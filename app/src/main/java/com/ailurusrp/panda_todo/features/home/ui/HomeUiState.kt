package com.ailurusrp.panda_todo.features.home.ui

import com.ailurusrp.panda_todo.features.home.data.model.BasicTask
import com.ailurusrp.panda_todo.features.home.data.model.RecurringTask
import com.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadline
import com.ailurusrp.panda_todo.features.home.ui.addtaskdialog.DialogStatus

data class HomeUiState(
    val recurringTaskData: List<RecurringTask> = emptyList(),
    val taskWithDeadlineData: List<TaskWithDeadline> = emptyList(),
    val basicTaskData: List<BasicTask> = emptyList(),

    val isDrawerOpen: Boolean = false,
    val dialogStatus: DialogStatus? = null,
    val selectedView: HomeViews = HomeViews.OpenTasks,
)