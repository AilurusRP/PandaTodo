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
import com.ailurusrp.panda_todo.features.home.data.model.RecurringTask
import com.ailurusrp.panda_todo.features.home.data.model.RecurringTaskRealm
import com.ailurusrp.panda_todo.features.home.domain.ResetInterval
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmUUID

@Composable
fun RecurringTaskItem(
    taskData: RecurringTask,
    onDeleteTask: (RealmUUID) -> Unit
) {
    val taskChecked = remember { mutableStateOf(taskData.completed) }

    val nextRecurrenceDate = DateUtils.format(taskData.nextRecurrenceDate)

    HomeListItem(
        taskData = taskData,

        additionalContent = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
            ) {
                Spacer(modifier = Modifier.width(50.dp))

                Text(
                    "Reset Interval: ${ResetInterval.valueOf(taskData.resetInterval).text}",
                    modifier = Modifier
                        .height(24.dp),
                    fontSize = 14.sp, color = Color.Gray
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
            ) {
                Spacer(modifier = Modifier.width(50.dp))

                Text(
                    "Next Recurrence Date: $nextRecurrenceDate",
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
                realm.query<RecurringTaskRealm>("id == $0", taskData.id).first().find()
                    ?.also { task ->
                        realm.writeBlocking {
                            if (findLatest(task)?.completed != null) {
                                if (findLatest(task)?.completed == true) {
                                    findLatest(task)?.completed = false
                                    findLatest(task)?.completionDate = null
                                    taskChecked.value = false
                                } else {
                                    findLatest(task)?.completed = true
                                    findLatest(task)?.completionDate = DateUtils.getTodayDate()
                                    taskChecked.value = true
                                }
                            }
                        }
                    }
            } finally {
                realm.close()
            }
        },

        onDeleteTask = onDeleteTask
    )
}