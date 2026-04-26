package com.ailurusrp.panda_todo.features.dailyreport.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ailurusrp.panda_todo.common.ui.Drawer
import com.ailurusrp.panda_todo.common.ui.LocalSnackbarHostState
import com.ailurusrp.panda_todo.common.ui.Views
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyReportScreen(navController: NavController) {

    val viewModel: DailyReportViewModel = viewModel(factory = DailyReportViewModelFactory())

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val globalSnackbar = LocalSnackbarHostState.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer(Views.DailyReport, navController)
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
                    title = { Text("Daily Report") },
                )
            },
            content = { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(8.dp)
                ) {
                    items(
                        items = viewModel.getAllDailyReports().reversed()
                    ) { dailyReport ->
                        Text(dailyReport.creationDate, fontSize = 18.sp)
                        Text(dailyReport.completedTasks.joinToString("\n"), fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                }
            }
        )

    }
}