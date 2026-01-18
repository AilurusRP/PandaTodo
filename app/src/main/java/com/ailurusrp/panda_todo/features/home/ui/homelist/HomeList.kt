package com.ailurusrp.panda_todo.features.home.ui.homelist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ailurusrp.panda_todo.features.home.data.model.BasicTask
import com.ailurusrp.panda_todo.features.home.data.model.RecurringTask
import com.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadline
import com.ailurusrp.panda_todo.features.home.ui.FilterMenuOptions
import io.realm.kotlin.types.RealmUUID

@Composable
fun HomeList(
    innerPadding: PaddingValues,
    basicTaskData: List<BasicTask>,
    recurringTaskData: List<RecurringTask>,
    taskWithDeadlineData: List<TaskWithDeadline>,
    filter: FilterMenuOptions,
    onDeleteBasicTask: (RealmUUID) -> Unit,
    onDeleteRecurringTask: (RealmUUID) -> Unit,
    onDeleteTaskWithDeadline: (RealmUUID) -> Unit
) {
    LazyColumn(
        modifier = Modifier.Companion
            .padding(innerPadding),
    ) {
        items(
            items = recurringTaskData
                .sortedBy { it.nextRecurrenceDate }
                .filter { it ->
                    it.completed == when (filter) {
                        FilterMenuOptions.OpenTasks -> false
                        FilterMenuOptions.CompletedTasks -> true
                    }
                },
            key = { task -> task.id.toString() }
        ) { task ->
            RecurringTaskItem(task, onDeleteRecurringTask)
        }

        items(
            items = taskWithDeadlineData.sortedBy { it.deadlineDate }.filter { it ->
                it.completed == when (filter) {
                    FilterMenuOptions.OpenTasks -> false
                    FilterMenuOptions.CompletedTasks -> true
                }
            },
            key = { task -> task.id.toString() }
        ) { task ->
            TaskWithDeadlineItem(task, onDeleteTaskWithDeadline)
        }

        items(
            items = basicTaskData.filter { it ->
                it.completed == when (filter) {
                    FilterMenuOptions.OpenTasks -> false
                    FilterMenuOptions.CompletedTasks -> true
                }
            },
            key = { task -> task.id.toString() }
        ) { task ->
            BasicTaskItem(task, onDeleteBasicTask)
        }
    }
}


