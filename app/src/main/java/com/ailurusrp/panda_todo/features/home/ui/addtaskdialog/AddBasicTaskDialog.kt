package com.ailurusrp.panda_todo.features.home.ui.addtaskdialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.ailurusrp.panda_todo.features.home.data.database.homeDatabaseConfig
import com.ailurusrp.panda_todo.features.home.data.model.BasicTask
import com.ailurusrp.panda_todo.features.home.data.model.BasicTaskRealm
import io.realm.kotlin.Realm

@Composable
fun AddBasicTaskDialog(
    onDialogStatusChange: (DialogStatus?) -> Unit,
    onTaskAdded: (BasicTask) -> Unit
) {
    val realm = remember(homeDatabaseConfig) { Realm.open(homeDatabaseConfig) }

    BasicAddTaskDialog(
        onDialogStatusChange,
        onOk = { newTaskName ->
            val basicTaskDataRealm = BasicTaskRealm().apply {
                name = newTaskName
                creationDate = System.currentTimeMillis()
            }

            try {
                realm.writeBlocking { copyToRealm(basicTaskDataRealm) }
                onTaskAdded(BasicTask.fromBasicTaskRealm(basicTaskDataRealm))
            } finally {
                realm.close()
            }
        }
    )
}