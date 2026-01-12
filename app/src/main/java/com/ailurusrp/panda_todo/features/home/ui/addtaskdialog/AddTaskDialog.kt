package com.ailurusrp.panda_todo.features.home.ui.addtaskdialog

import androidx.compose.runtime.Composable
import com.ailurusrp.panda_todo.features.home.data.model.BasicTask
import com.ailurusrp.panda_todo.features.home.data.model.RecurringTask
import com.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadline

@Composable
fun AddTaskDialog(
    dialogStatus: DialogStatus?,
    onDialogStatusChange: (DialogStatus?) -> Unit,
    onBasicTaskAdded: (BasicTask) -> Unit,
    onRecurrenceTaskAdded: (RecurringTask) -> Unit,
    onTaskWithDeadlineAdded: (TaskWithDeadline) -> Unit
) {
    when (dialogStatus) {
        DialogStatus.ShowAddBasicTaskDialog -> {
            AddBasicTaskDialog(onDialogStatusChange, onBasicTaskAdded)
        }

        DialogStatus.ShowAddRecurringTaskDialog -> {
            AddRecurringTaskDialog(onDialogStatusChange, onRecurrenceTaskAdded)
        }

        DialogStatus.ShowAddTaskWithDeadlineDialog -> {
            AddTaskWithDeadlineDialog(onDialogStatusChange, onTaskWithDeadlineAdded)
        }

        null -> {}
    }
}


enum class DialogStatus {
    ShowAddBasicTaskDialog, ShowAddRecurringTaskDialog, ShowAddTaskWithDeadlineDialog
}
