package com.ailurusrp.panda_todo.features.home.ui.homelist

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ailurusrp.panda_todo.common.utils.DateUtils
import com.ailurusrp.panda_todo.features.home.data.database.homeDatabaseConfig
import com.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadline
import com.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadlineRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmUUID

@Composable
fun TaskWithDeadlineItem(taskData: TaskWithDeadline, onDeleteTask: (RealmUUID) -> Unit) {
    val taskChecked = remember { mutableStateOf(taskData.completed) }

    HomeListItem(
        taskData,
        additionalContent = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
            ) {
                Spacer(modifier = Modifier.width(50.dp))

                Text(
                    "Deadline: ${DateUtils.format(taskData.deadlineDate)}",
                    modifier = Modifier
                        .height(24.dp),
                    fontSize = 14.sp, color = Color.Gray
                )
            }
        },
        taskChecked = taskChecked,
        onCheckedChange = {
            val realm = Realm.Companion.open(homeDatabaseConfig)
            try {
                realm.query<TaskWithDeadlineRealm>("id == $0", taskData.id).first().find()
                    ?.also { task ->
                        realm.writeBlocking {
                            if (findLatest(task)?.completed != null) {
                                findLatest(task)?.completed = !findLatest(task)?.completed!!
                            }
                        }
                    }
            } finally {
                realm.close()
            }

            taskChecked.value = !taskChecked.value
        },
        onDeleteTask
    )
}