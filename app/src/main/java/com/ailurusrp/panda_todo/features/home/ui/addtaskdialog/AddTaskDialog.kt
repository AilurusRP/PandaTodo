package com.ailurusrp.panda_todo.features.home.ui.addtaskdialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AddTaskDialog(dialogStatus: DialogStatus?, onDialogStatusChange: (DialogStatus?) -> Unit) {
    var newTaskName by remember { mutableStateOf("") }

    when (dialogStatus) {
        DialogStatus.ShowAddBasicTaskDialog -> {
            BasicAddTaskDialog(onDialogStatusChange)
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
