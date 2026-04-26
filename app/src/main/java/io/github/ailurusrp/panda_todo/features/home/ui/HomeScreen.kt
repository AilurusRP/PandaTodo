package io.github.ailurusrp.panda_todo.features.home.ui

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
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
import androidx.navigation.NavController
import io.github.ailurusrp.panda_todo.common.ui.Drawer
import io.github.ailurusrp.panda_todo.common.ui.LocalSnackbarHostState
import io.github.ailurusrp.panda_todo.common.ui.Views
import io.github.ailurusrp.panda_todo.features.home.ui.addtaskdialog.AddTaskDialog
import io.github.ailurusrp.panda_todo.features.home.ui.homelist.HomeList
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory())
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val globalSnackbar = LocalSnackbarHostState.current

    LaunchedEffect(uiState.isDrawerOpen) {
        if (uiState.isDrawerOpen) drawerState.open()
        else drawerState.close()
    }

    LaunchedEffect(uiState.selectedMenuOptions) {
        uiState.recurringTaskData.forEach { taskData ->
            if (taskData.needUpdateCompletionStatus) {
                viewModel.updateRecurringTaskCompletionState(RealmUUID.from(taskData.id), false)
                viewModel.updateRecurringTaskCompletionDate(RealmUUID.from(taskData.id), null)
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer(Views.Home, navController)
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(globalSnackbar) },

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

