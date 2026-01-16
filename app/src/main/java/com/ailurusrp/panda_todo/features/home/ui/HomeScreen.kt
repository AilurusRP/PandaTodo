package com.ailurusrp.panda_todo.features.home.ui

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.ailurusrp.panda_todo.features.home.data.database.homeDatabaseConfig
import com.ailurusrp.panda_todo.features.home.data.model.BasicTask
import com.ailurusrp.panda_todo.features.home.data.model.BasicTaskRealm
import com.ailurusrp.panda_todo.features.home.data.model.RecurringTask
import com.ailurusrp.panda_todo.features.home.data.model.RecurringTaskRealm
import com.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadline
import com.ailurusrp.panda_todo.features.home.data.model.TaskWithDeadlineRealm
import com.ailurusrp.panda_todo.features.home.ui.addtaskdialog.AddTaskDialog
import com.ailurusrp.panda_todo.features.home.ui.addtaskdialog.DialogStatus
import com.ailurusrp.panda_todo.features.home.ui.homelist.HomeList
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var dialogStatus by remember { mutableStateOf<DialogStatus?>(null) }

    var basicTaskData by remember { mutableStateOf<List<BasicTask>>(listOf()) }
    var recurringTaskData by remember { mutableStateOf<List<RecurringTask>>(listOf()) }
    var taskWithDeadlineData by remember { mutableStateOf<List<TaskWithDeadline>>(listOf()) }

    LaunchedEffect(Unit) {
        val realm = Realm.open(homeDatabaseConfig)
        try {
            basicTaskData = realm.query<BasicTaskRealm>().find().toMutableList()
                .map { it -> BasicTask.fromBasicTaskRealm(it) }
            recurringTaskData = realm.query<RecurringTaskRealm>().find().toMutableList()
                .map { it -> RecurringTask.fromRecurringTaskRealm(it) }
            taskWithDeadlineData = realm.query<TaskWithDeadlineRealm>().find().toMutableList()
                .map { it -> TaskWithDeadline.fromTaskWithDeadlineRealm(it) }
        } finally {
            realm.close()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { Drawer() }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Companion.Black,
                        titleContentColor = Color.Companion.White,
                        actionIconContentColor = Color.Companion.White,
                        navigationIconContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Open the Drawer"
                            )
                        }
                    },
                    title = { Text("Panda Todo") },
                    actions = {
                        AddTaskMenuButton({ dialogStatus = it })
                        FilterMenuButton()
                    }
                )
            }
        ) { innerPadding ->
            HomeList(
                innerPadding,
                basicTaskData,
                recurringTaskData,
                taskWithDeadlineData,
                onDeleteBasicTask = { id -> basicTaskData -= basicTaskData.first { item -> item.id == id } },
                onDeleteRecurringTask = { id -> recurringTaskData -= recurringTaskData.first { item -> item.id == id } },
                onDeleteTaskWithDeadline = { id -> taskWithDeadlineData -= taskWithDeadlineData.first { item -> item.id == id } }
            )
        }

        AddTaskDialog(
            dialogStatus = dialogStatus,
            onDialogStatusChange = { dialogStatus = it },
            onBasicTaskAdded = { taskData -> basicTaskData += taskData },
            onRecurrenceTaskAdded = { taskData -> recurringTaskData += taskData },
            onTaskWithDeadlineAdded = { taskData -> taskWithDeadlineData += taskData }
        )
    }
}

