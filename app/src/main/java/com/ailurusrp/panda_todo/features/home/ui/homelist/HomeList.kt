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
import io.realm.kotlin.types.RealmUUID

@Composable
fun HomeList(
    innerPadding: PaddingValues,
    basicTaskData: List<BasicTask>,
    recurringTaskData: List<RecurringTask>,
    taskWithDeadlineData: List<TaskWithDeadline>,
    onDeleteBasicTask: (RealmUUID) -> Unit,
    onDeleteRecurringTask: (RealmUUID) -> Unit,
    onDeleteTaskWithDeadline: (RealmUUID) -> Unit
) {
    LazyColumn(
        modifier = Modifier.Companion
            .padding(innerPadding),
    ) {
        items(recurringTaskData.sortedBy { it.nextRecurrenceDate }) { task ->
            RecurringTaskItem(task, onDeleteRecurringTask)
        }

        items(taskWithDeadlineData.sortedBy { it.deadlineDate }) { task ->
            TaskWithDeadlineItem(task, onDeleteTaskWithDeadline)
        }

        items(basicTaskData) { task ->
            BasicTaskItem(task, onDeleteBasicTask)
        }
    }
}


