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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ailurusrp.panda_todo.features.home.ui.addtaskdialog.AddTaskDialog
import com.ailurusrp.panda_todo.features.home.ui.homelist.HomeList
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory())) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.isDrawerOpen) {
        if (uiState.isDrawerOpen) drawerState.open()
        else drawerState.close()
    }

    LaunchedEffect(uiState.selectedView) {
        uiState.recurringTaskData.forEach { taskData ->
            if (taskData.needUpdateCompletionStatus) {
                viewModel.updateRecurringTaskCompletionState(taskData.id, false)
                viewModel.updateRecurringTaskCompletionDate(taskData.id, null)
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer(
                uiState.selectedView,
                onSelected = { selected ->
                    viewModel.changeSelectedView(selected)
                    scope.launch {
                        drawerState.apply { close() }
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black,
                        titleContentColor = Color.White,
                        actionIconContentColor = Color.White,
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
                        AddTaskMenuButton({ viewModel.changeDialogStatus(it) })
                        FilterMenuButton()
                    }
                )
            }
        ) { innerPadding ->
            HomeList(
                innerPadding,
                uiState.basicTaskData,
                uiState.recurringTaskData,
                uiState.taskWithDeadlineData,
                filter = uiState.selectedMenuOptions,
            )
        }

        AddTaskDialog(
            dialogStatus = uiState.dialogStatus,
            onDialogStatusChange = { viewModel.changeDialogStatus(it) }
        )
    }
}

