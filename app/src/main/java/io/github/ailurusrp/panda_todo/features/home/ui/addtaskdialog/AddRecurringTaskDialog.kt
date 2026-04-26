package io.github.ailurusrp.panda_todo.features.home.ui.addtaskdialog

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
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.ailurusrp.panda_todo.common.utils.DateUtils
import io.github.ailurusrp.panda_todo.features.home.data.model.RecurringTaskRealm
import io.github.ailurusrp.panda_todo.features.home.domain.ResetInterval
import io.github.ailurusrp.panda_todo.features.home.ui.HomeViewModel
import io.github.ailurusrp.panda_todo.features.home.ui.HomeViewModelFactory

@Composable
fun AddRecurringTaskDialog(
    onDialogStatusChange: (DialogStatus?) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory())
) {
    val resetInterval = remember { mutableStateOf(ResetInterval.OneDay) }

    BasicAddTaskDialog(
        onDialogStatusChange,

        onOk = { newTaskName ->
            val recurringTaskDataRealm = RecurringTaskRealm().apply {
                name = newTaskName
                creationDate = DateUtils.getTodayDate()
                this.resetInterval = resetInterval.value.toString()
            }

            viewModel.addRecurringTask(recurringTaskDataRealm)
        },

        additionalContent = {
            Spacer(modifier = Modifier.height(36.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                var resetIntervalMenuExpanded by remember { mutableStateOf(false) }

                Text("Reset interval:", fontSize = 18.sp)

                Box {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp)
                            .border(BorderStroke(1.dp, Color.Black))
                            .clickable(onClick = { resetIntervalMenuExpanded = true }),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(resetInterval.value.text, color = Color.Black)
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

