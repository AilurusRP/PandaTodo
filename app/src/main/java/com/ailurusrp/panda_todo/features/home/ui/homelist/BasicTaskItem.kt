package com.ailurusrp.panda_todo.features.home.ui.homelist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ailurusrp.panda_todo.common.utils.DateUtils
import com.ailurusrp.panda_todo.features.home.data.database.homeDatabaseConfig
import com.ailurusrp.panda_todo.features.home.data.model.BasicTask
import com.ailurusrp.panda_todo.features.home.data.model.BasicTaskRealm
import com.ailurusrp.panda_todo.features.home.ui.HomeViewModel
import com.ailurusrp.panda_todo.features.home.ui.HomeViewModelFactory
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

@Composable
fun BasicTaskItem(
    taskData: BasicTask,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory())
) {

    val taskChecked = remember { mutableStateOf(taskData.completed) }

    HomeListItem(
        taskData,
        taskChecked = taskChecked,

        onCheckedChange = {
            val realm = Realm.open(homeDatabaseConfig)
            try {
                realm.query<BasicTaskRealm>("id == $0", taskData.id).first().find()?.also { task ->
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