package com.ailurusrp.panda_todo.features.home.ui.homelist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.ailurusrp.panda_todo.features.home.data.database.homeDatabaseConfig
import com.ailurusrp.panda_todo.features.home.data.model.BasicTask
import com.ailurusrp.panda_todo.features.home.data.model.BasicTaskRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmUUID

@Composable
fun BasicTaskItem(taskData: BasicTask, onDeleteTask: (RealmUUID) -> Unit) {

    val taskChecked = remember { mutableStateOf(taskData.completed) }

    HomeListItem(
        taskData,
        taskChecked = taskChecked,

        onCheckedChange = {
            val realm = Realm.Companion.open(homeDatabaseConfig)
            try {
                realm.query<BasicTaskRealm>("id == $0", taskData.id).first().find()?.also { task ->
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

        onDeleteTask = onDeleteTask
    )
}