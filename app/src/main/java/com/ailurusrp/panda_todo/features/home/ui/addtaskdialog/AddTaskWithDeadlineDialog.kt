package com.ailurusrp.panda_todo.features.home.ui.addtaskdialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ailurusrp.panda_todo.common.utils.DateUtils
import com.ailurusrp.panda_todo.features.home.data.database.homeDatabaseConfig
import com.ailurusrp.panda_todo.features.home.data.model.BasicTask
import com.ailurusrp.panda_todo.features.home.data.model.BasicTaskRealm
import com.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadline
import com.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadlineRealm
import com.ailurusrp.panda_todo.features.home.ui.homelist.TaskWithDeadlineItem
import io.realm.kotlin.Realm
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun AddTaskWithDeadlineDialog(
    onDialogStatusChange: (DialogStatus?) -> Unit,
    onTaskAdded: (TaskWithDeadline) -> Unit
) {
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableLongStateOf(DateUtils.toLong(LocalDateTime.now())) }
    val realm = remember(homeDatabaseConfig) { Realm.open(homeDatabaseConfig) }

    BasicAddTaskDialog(
        onDialogStatusChange,

        onOk = { newTaskName ->
            val taskWithDeadlineRealm = TaskWithDeadlineRealm().apply {
                name = newTaskName
                creationDate = DateUtils.getTodayDate()
                deadlineDate = selectedDate
            }

            try {
                realm.writeBlocking { copyToRealm(taskWithDeadlineRealm) }
                onTaskAdded(TaskWithDeadline.fromTaskWithDeadlineRealm(taskWithDeadlineRealm))
            } finally {
                realm.close()
            }
        },

        additionalContent = {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Spacer(modifier = Modifier.width(18.dp))
                Box(
                    modifier = Modifier.Companion
                        .border(
                            BorderStroke(
                                width = 1.dp,
                                color = Color.Companion.Black
                            )
                        )
                        .height(32.dp)
                        .width(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        DateUtils.format(selectedDate),
                        fontSize = 16.sp
                    )
                }

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    IconButton(
                        onClick = { showDatePickerDialog = true }
                    ) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = "Show date picker dialog."
                        )
                    }

                    if (showDatePickerDialog) DatePickerModal(
                        onDateSelected = { selectedDate = it },
                        onDismiss = { showDatePickerDialog = false }
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                if (datePickerState.selectedDateMillis != null) {
                    onDateSelected(
                        datePickerState.selectedDateMillis!!
                    )
                }
                onDismiss()
            }) {
                Text("OK", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.Black)
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                selectedYearContainerColor = Color.Gray,
                selectedDayContainerColor = Color.Gray,
                todayDateBorderColor = Color.Gray,
                todayContentColor = Color.Gray,
                currentYearContentColor = Color.Gray
            )
        )
    }
}
