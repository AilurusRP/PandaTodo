package com.ailurusrp.panda_todo.features.home.ui.homelist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ailurusrp.panda_todo.common.utils.DateUtils
import com.ailurusrp.panda_todo.features.dailyreport.ui.DailyReportViewModel
import com.ailurusrp.panda_todo.features.dailyreport.ui.DailyReportViewModelFactory
import com.ailurusrp.panda_todo.features.home.data.database.homeDatabaseConfig
import com.ailurusrp.panda_todo.features.home.data.model.BasicTask
import com.ailurusrp.panda_todo.features.home.data.model.BasicTaskRealm
import com.ailurusrp.panda_todo.features.home.ui.HomeViewModel
import com.ailurusrp.panda_todo.features.home.ui.HomeViewModelFactory
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmUUID

@Composable
fun BasicTaskItem(
    taskData: BasicTask,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory())
) {

    val taskChecked = remember { mutableStateOf(taskData.completed) }

    val dailyReportViewModel: DailyReportViewModel =
        viewModel(factory = DailyReportViewModelFactory())

    HomeListItem(
        taskData,
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
                realm.query<BasicTaskRealm>(
                    "id == $0",
                    RealmUUID.from(taskData.id)
                ).first().find()?.also { task ->
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

        onDeleteTask = viewModel::deleteBasicTask
    )
}