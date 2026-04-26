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

@Composable
fun HomeList(
    innerPadding: PaddingValues,
    basicTaskData: List<BasicTask>,
    recurringTaskData: List<RecurringTask>,
    taskWithDeadlineData: List<TaskWithDeadline>,
    filter: FilterMenuOptions
) {
    LazyColumn(
        modifier = Modifier.padding(innerPadding)
    ) {
        items(
            items = recurringTaskData
                .sortedBy { it.nextRecurrenceDate }
                .filter {
                    it.completed == when (filter) {
                        FilterMenuOptions.OpenTasks -> false
                        FilterMenuOptions.CompletedTasks -> true
                    }
                },
            key = { task -> task.id.toString() }
        ) { task ->
            RecurringTaskItem(task)
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
            TaskWithDeadlineItem(task)
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
            BasicTaskItem(task)
        }
    }
}


