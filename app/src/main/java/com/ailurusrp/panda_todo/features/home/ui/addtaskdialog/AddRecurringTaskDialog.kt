package com.ailurusrp.panda_todo.features.home.ui.addtaskdialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ailurusrp.panda_todo.features.home.data.database.homeDatabaseConfig
import com.ailurusrp.panda_todo.features.home.data.model.RecurringTask
import com.ailurusrp.panda_todo.features.home.data.model.RecurringTaskRealm
import com.ailurusrp.panda_todo.features.home.domain.ResetInterval
import io.realm.kotlin.Realm
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun AddRecurringTaskDialog(
    onDialogStatusChange: (DialogStatus?) -> Unit,
    onTaskAdded: (RecurringTask) -> Unit
) {
    val realm = remember(homeDatabaseConfig) { Realm.open(homeDatabaseConfig) }
    val resetInterval = remember { mutableStateOf(ResetInterval.OneDay) }

    BasicAddTaskDialog(
        onDialogStatusChange,

        onOk = { newTaskName ->
            val zone: ZoneId = ZoneId.systemDefault()
            val recurringTaskDataRealm = RecurringTaskRealm().apply {
                name = newTaskName
                creationDate =
                    LocalDate.now(zone).atStartOfDay(zone).plusDays(1).toInstant().toEpochMilli()
                this.resetInterval = resetInterval.value.toString()
            }

            try {
                realm.writeBlocking { copyToRealm(recurringTaskDataRealm) }
                onTaskAdded(RecurringTask.fromRecurringTaskRealm(recurringTaskDataRealm))
            } finally {
                realm.close()
            }
        },

        additionalContent = {
            Spacer(modifier = Modifier.Companion.height(36.dp))

            Row(
                verticalAlignment = Alignment.Companion.CenterVertically,
                modifier = Modifier.Companion.padding(vertical = 8.dp)
            ) {
                var resetIntervalMenuExpanded by remember { mutableStateOf(false) }

                Text("Reset interval:", fontSize = 18.sp)

                Box {
                    Box(
                        modifier = Modifier.Companion
                            .fillMaxWidth()
                            .height(32.dp)
                            .border(BorderStroke(1.dp, Color.Companion.Black))
                            .clickable(onClick = { resetIntervalMenuExpanded = true }),
                        contentAlignment = Alignment.Companion.Center
                    ) {
                        Text(resetInterval.value.text, color = Color.Companion.Black)
                    }

                    DropdownMenu(
                        expanded = resetIntervalMenuExpanded,
                        onDismissRequest = { resetIntervalMenuExpanded = false }
                    ) {
                        ResetInterval.entries.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option.text) },
                                onClick = {
                                    resetInterval.value = option
                                    resetIntervalMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        })
}

