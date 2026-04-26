package io.github.ailurusrp.panda_todo.features.home.ui.homelist

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
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.ailurusrp.panda_todo.common.utils.DateUtils
import io.github.ailurusrp.panda_todo.features.dailyreport.ui.DailyReportViewModel
import io.github.ailurusrp.panda_todo.features.dailyreport.ui.DailyReportViewModelFactory
import io.github.ailurusrp.panda_todo.features.home.data.database.homeDatabaseConfig
import io.github.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadline
import io.github.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadlineRealm
import io.github.ailurusrp.panda_todo.features.home.ui.HomeViewModel
import io.github.ailurusrp.panda_todo.features.home.ui.HomeViewModelFactory
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmUUID

@Composable
fun TaskWithDeadlineItem(
    taskData: TaskWithDeadline,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory())
) {
    val taskChecked = remember { mutableStateOf(taskData.completed) }

    val dailyReportViewModel: DailyReportViewModel =
        viewModel(factory = DailyReportViewModelFactory())

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
            if (taskChecked.value) {
                dailyReportViewModel.deleteCompletedTaskFromDailyReport(
                    DateUtils.format(
                        DateUtils.getTodayDate()
                    ), taskData.name
                )

            } else {
                dailyReportViewModel.addNewCompletedTaskToDailyReportOrAddDailyReport(
                    DateUtils.format(
                        DateUtils.getTodayDate()
                    ), taskData.name
                )

            }

            val realm = Realm.open(homeDatabaseConfig)
            try {
                realm.query<TaskWithDeadlineRealm>("id == $0", RealmUUID.from(taskData.id))
                    .first()
                    .find()
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
        onDeleteTask = viewModel::deleteTaskWithDeadline
    )
}