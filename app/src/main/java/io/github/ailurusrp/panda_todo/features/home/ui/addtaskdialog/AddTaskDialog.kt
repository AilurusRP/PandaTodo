package io.github.ailurusrp.panda_todo.features.home.ui.addtaskdialog

import androidx.compose.runtime.Composable

@Composable
fun AddTaskDialog(
    dialogStatus: DialogStatus?,
    onDialogStatusChange: (DialogStatus?) -> Unit
) {
    when (dialogStatus) {
        DialogStatus.ShowAddBasicTaskDialog -> {
            AddBasicTaskDialog(onDialogStatusChange)
        }

        DialogStatus.ShowAddRecurringTaskDialog -> {
            AddRecurringTaskDialog(onDialogStatusChange)
        }

        DialogStatus.ShowAddTaskWithDeadlineDialog -> {
            AddTaskWithDeadlineDialog(onDialogStatusChange)
        }

        null -> {}
    }
}


enum class DialogStatus {
    ShowAddBasicTaskDialog, ShowAddRecurringTaskDialog, ShowAddTaskWithDeadlineDialog
}
