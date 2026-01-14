package com.ailurusrp.panda_todo.features.home.ui.addtaskdialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun BasicAddTaskDialog(
    onDialogStatusChange: (DialogStatus?) -> Unit,
    onOk: (String) -> Unit,
    additionalContent: @Composable () -> Unit = {}
) {
    val newTaskName = rememberTextFieldState(initialText = "")

    Dialog(
        onDismissRequest = { onDialogStatusChange(null) }
    ) {
        Card(modifier = Modifier.Companion.width(250.dp)) {
            Column(
                modifier = Modifier.Companion.padding(
                    top = 24.dp,
                    start = 18.dp,
                    end = 18.dp,
                    bottom = 8.dp
                )
            ) {
                Text("Task Name:", fontSize = 20.sp)

                Spacer(modifier = Modifier.Companion.height(14.dp))

                DialogTextField(state = newTaskName)

                additionalContent()

                Spacer(modifier = Modifier.Companion.height(14.dp))

                Row(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(
                        onClick = {
                            onOk(newTaskName.text.toString())
                            onDialogStatusChange(null)
                        }
                    ) { Text("Ok", color = Color.Companion.Black) }

                    TextButton(
                        onClick = { onDialogStatusChange(null) }
                    ) { Text("Cancel", color = Color.Companion.Black) }
                }
            }
        }
    }
}

@Composable
fun DialogTextField(state: TextFieldState) {
    BasicTextField(
        state = state,
        textStyle = TextStyle(fontSize = 16.sp),
        decorator = { innerTextField ->
            Column {
                Box(modifier = Modifier.Companion.padding(10.dp)) {
                    innerTextField()
                }

                Box(modifier = Modifier.Companion.padding(horizontal = 10.dp)) {
                    Box(
                        modifier = Modifier.Companion
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(color = Color.Companion.Black)
                    )
                }
            }

        }
    )
}
