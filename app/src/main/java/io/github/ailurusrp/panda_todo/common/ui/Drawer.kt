package io.github.ailurusrp.panda_todo.common.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import io.github.ailurusrp.panda_todo.features.backup.data.BackupRepository
import io.github.ailurusrp.panda_todo.features.backup.ui.BackupEvent
import io.github.ailurusrp.panda_todo.features.backup.ui.BackupViewModel
import io.github.ailurusrp.panda_todo.features.dailyreport.data.DailyReportRepository
import io.github.ailurusrp.panda_todo.features.dailyreport.data.dailyReportDatabaseConfig
import io.github.ailurusrp.panda_todo.features.home.data.database.homeDatabaseConfig
import io.github.ailurusrp.panda_todo.features.home.data.repository.TaskRepository
import io.realm.kotlin.Realm
import kotlinx.coroutines.launch

@Composable
fun Drawer(currentView: Views, navController: NavController) {
    val viewModel: BackupViewModel = viewModel<BackupViewModel> {
        BackupViewModel(
            BackupRepository(
                taskRepository = TaskRepository(
                    realm = Realm.open(homeDatabaseConfig)
                ),
                dailyReportRepository = DailyReportRepository(
                    realm = Realm.open(dailyReportDatabaseConfig)
                )
            )
        )
    }

    var selectedView by remember { mutableStateOf(currentView) }

    val globalSnackbar = LocalSnackbarHostState.current
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        if (uri != null) {
            val outputStream = context.contentResolver.openOutputStream(uri)
            if (outputStream != null) {
                scope.launch {
                    viewModel.exportBackup(outputStream)
                }
            } else {
                errorMessage = "Failed To Create File!"
            }
        }
    }

    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            val inputStream = context.contentResolver.openInputStream(uri)
            if (inputStream != null) {
                scope.launch {
                    viewModel.importBackup(inputStream)
                }
            } else {
                errorMessage = "Failed To Import File!"
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is BackupEvent.Success -> {
                    globalSnackbar.showSnackbar(event.message)
                }

                is BackupEvent.Error -> {
                    errorMessage = event.message
                }
            }
        }
    }

    if (errorMessage != null) {
        AlertDialog(
            onDismissRequest = { errorMessage = null },
            title = { Text("Error") },
            text = { Text(errorMessage!!) },
            confirmButton = {
                TextButton(onClick = { errorMessage = null }) {
                    Text("Ok")
                }
            }
        )
    }

    ModalDrawerSheet(drawerContainerColor = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxWidth(.75f)
                .padding(vertical = 8.dp)
        ) {
            Text("Views", fontSize = 26.sp, fontWeight = FontWeight.Bold)

            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                Views.entries.forEach { view ->
                    if (selectedView == view) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(36.dp)
                                .background(color = Color.LightGray.copy(alpha = .4f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(fontSize = 20.sp, text = view.text)
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(36.dp)
                                .clickable(
                                    onClick = {
                                        selectedView = view
                                        navController.navigate(view.nav)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(fontSize = 20.sp, text = view.text)
                        }
                    }
                }
            }
        }

        HorizontalDivider(
            color = Color.LightGray,
            thickness = .5.dp,
            modifier = Modifier.fillMaxWidth(0.75f)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = {
                    exportLauncher.launch("panda_todo.backup.json")
                },
                modifier = Modifier.width(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(4.dp)
            ) { Text("Export") }

            Button(
                onClick = { importLauncher.launch(arrayOf("application/json")) },
                modifier = Modifier.width(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
            ) { Text("Import") }
        }
    }
}

enum class Views(val text: String, val nav: String) {
    Home("Home", "home"), DailyReport("Daily Report", "daily_report")
}