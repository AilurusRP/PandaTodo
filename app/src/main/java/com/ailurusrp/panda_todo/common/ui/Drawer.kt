package com.ailurusrp.panda_todo.common.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ailurusrp.panda_todo.features.backup.data.BackupRepository
import com.ailurusrp.panda_todo.features.backup.ui.BackupEvent
import com.ailurusrp.panda_todo.features.backup.ui.BackupViewModel
import com.ailurusrp.panda_todo.features.home.data.database.homeDatabaseConfig
import com.ailurusrp.panda_todo.features.home.data.repository.TaskRepository
import io.realm.kotlin.Realm
import kotlinx.coroutines.launch

@Composable
fun Drawer() {
    val viewModel: BackupViewModel = viewModel<BackupViewModel> {
        BackupViewModel(
            BackupRepository(
                taskRepository = TaskRepository(
                    realm = Realm.open(homeDatabaseConfig)
                )
            )
        )
    }

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

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is BackupEvent.Success -> {
                    globalSnackbar.showSnackbar("Tasks Successfully Exported!")
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
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Views", fontSize = 26.sp, fontWeight = FontWeight.Bold)
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
                onClick = {},
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

enum class HomeViews(val text: String) {
    OpenTasks("Open Tasks"), CompletedTasks("Completed Tasks")
}