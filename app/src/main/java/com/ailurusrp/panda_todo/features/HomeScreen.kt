package com.ailurusrp.panda_todo.features

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ailurusrp.panda_todo.ui.theme.LightGray

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                title = { Text("Panda Todo") },
                actions = {
                    IconButton(
                        onClick = {},
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Tasks"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding),
        ) {
            items(12) {
                HomeListItem()
            }
        }
    }
}

@Composable
fun HomeListItem() {

    val taskChecked = remember { mutableStateOf(false) }
    val menuExpanded = remember() { mutableStateOf(false) }

    Surface(
        color = Color.White,
        modifier = Modifier
            .height(65.dp)
            .fillMaxSize(),
        border = BorderStroke(
            width = 0.1.dp, color = LightGray
        )
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = taskChecked.value,
                onCheckedChange = { taskChecked.value = !taskChecked.value },
                colors = CheckboxDefaults.colors(checkedColor = Color.Gray)
            )

            Box(modifier = Modifier.weight(1f)) {
                Text("任务 | Task Name", fontSize = 16.sp)
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
                    modifier = Modifier.background(color = Color.White)
                ) {
                    DropdownMenuItem(
                        onClick = {},
                        text = { Text("Edit Task") },
                    )

                    DropdownMenuItem(
                        onClick = {},
                        text = { Text("Delete Task") }
                    )
                }
            }
        }

    }
}
