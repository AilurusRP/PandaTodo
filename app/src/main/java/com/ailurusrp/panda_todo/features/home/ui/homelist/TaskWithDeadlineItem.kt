package com.ailurusrp.panda_todo.features.home.ui.homelist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadlineRealm
import io.realm.kotlin.types.RealmUUID

@Composable
fun TaskWithDeadlineItem(taskData: TaskWithDeadlineRealm, onDeleteTask: (RealmUUID) -> Unit) {
    val taskChecked = remember { mutableStateOf(taskData.completed) }

    HomeListItem(taskData, additionalContent = {
        Text("Deadline: $")
    }, taskChecked = taskChecked, onCheckedChange = {}, onDeleteTask)
}