package com.ailurusrp.panda_todo.features.home.ui.addtaskdialog

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ailurusrp.panda_todo.common.utils.DateUtils
import com.ailurusrp.panda_todo.features.home.data.model.BasicTaskRealm
import com.ailurusrp.panda_todo.features.home.ui.HomeViewModel
import com.ailurusrp.panda_todo.features.home.ui.HomeViewModelFactory

@Composable
fun AddBasicTaskDialog(
    onDialogStatusChange: (DialogStatus?) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory())
) {
    BasicAddTaskDialog(
        onDialogStatusChange,

        onOk = { newTaskName ->
            val basicTaskDataRealm = BasicTaskRealm().apply {
                name = newTaskName
                creationDate = DateUtils.getTodayDate()
            }

            viewModel.addBasicTask(basicTaskDataRealm)
        }
    )
}