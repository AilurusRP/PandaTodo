package com.ailurusrp.panda_todo.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ailurusrp.panda_todo.features.home.ui.addtaskdialog.DialogStatus

@Composable
fun AddTaskMenuButton(showDialog: (DialogStatus) -> Unit) {
    var addMenuExpanded by remember { mutableStateOf(false) }

    Box {
        IconButton(
            onClick = { addMenuExpanded = true },
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add Tasks",
                modifier = Modifier.size(26.dp)
            )
        }

        DropdownMenu(
            expanded = addMenuExpanded,
            onDismissRequest = { addMenuExpanded = false },
            modifier = Modifier.Companion.background(color = Color.Companion.White)
        ) {
            DropdownMenuItem(
                onClick = {
                    showDialog(DialogStatus.ShowAddBasicTaskDialog)
                    addMenuExpanded = false
                },
                text = { Text("Basic Task") },
            )

            DropdownMenuItem(
                onClick = {
                    showDialog(DialogStatus.ShowAddRecurringTaskDialog)
                    addMenuExpanded = false
                },
                text = { Text("Recurring Task") }
            )

            DropdownMenuItem(
                onClick = {
                    showDialog(DialogStatus.ShowAddTaskWithDeadlineDialog)
                    addMenuExpanded = false
                },
                text = { Text("Task With Deadline") }
            )
        }
    }
}
