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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddTaskMenuButton() {
    val addMenuExpanded = remember { mutableStateOf(false) }

    Box {
        IconButton(
            onClick = { addMenuExpanded.value = true },
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add Tasks",
                modifier = Modifier.size(26.dp)
            )
        }

        DropdownMenu(
            expanded = addMenuExpanded.value,
            onDismissRequest = { addMenuExpanded.value = false },
            modifier = Modifier.Companion.background(color = Color.Companion.White)
        ) {
            DropdownMenuItem(
                onClick = {},
                text = { Text("Basic Task") },
            )

            DropdownMenuItem(
                onClick = {},
                text = { Text("Recurring Task") }
            )

            DropdownMenuItem(
                onClick = {},
                text = { Text("Task With Deadline") }
            )
        }
    }
}