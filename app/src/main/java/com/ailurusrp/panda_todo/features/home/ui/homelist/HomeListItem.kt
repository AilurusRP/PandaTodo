package com.ailurusrp.panda_todo.features.home.ui.homelist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ailurusrp.panda_todo.features.home.data.database.homeDatabaseConfig
import com.ailurusrp.panda_todo.features.home.data.model.BasicTaskRealm
import com.ailurusrp.panda_todo.features.home.data.model.Task
import com.ailurusrp.panda_todo.common.ui.theme.LightGray
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmUUID

@Composable
fun HomeListItem(
    taskData: Task,
    additionalContent: @Composable () -> Unit = {},
    taskChecked: MutableState<Boolean>,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteTask: (RealmUUID) -> Unit
) {

    val menuExpanded = remember { mutableStateOf(false) }

    Surface(
        color = Color.Companion.White,
        modifier = Modifier.Companion
//            .height(65.dp)
            .fillMaxSize(),
        border = BorderStroke(
            width = 0.1.dp, color = LightGray
        )
    ) {
        Column {
            Row(
                modifier = Modifier.Companion.fillMaxSize(),
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Checkbox(
                    checked = taskChecked.value,
                    onCheckedChange = onCheckedChange,
                    colors = CheckboxDefaults.colors(checkedColor = Color.Companion.Gray)
                )

                Box(modifier = Modifier.Companion.weight(1f)) {
                    Text(taskData.name, fontSize = 16.sp)
                }

                Box {
                    IconButton(
                        onClick = { menuExpanded.value = true }
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "More Options"
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpanded.value,
                        onDismissRequest = { menuExpanded.value = false },
                        modifier = Modifier.Companion.background(color = Color.Companion.White)
                    ) {
                        DropdownMenuItem(
                            onClick = {},
                            text = { Text("Edit Task") },
                        )

                        DropdownMenuItem(
                            onClick = { onDeleteTask(taskData.id) },
                            text = { Text("Delete Task") }
                        )
                    }
                }
            }

            additionalContent()
        }
    }
}