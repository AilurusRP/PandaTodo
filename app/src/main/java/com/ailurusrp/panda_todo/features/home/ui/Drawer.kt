package com.ailurusrp.panda_todo.features.home.ui

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Drawer(selectedView: HomeViews, onSelected: (HomeViews) -> Unit) {
    val viewsOptions = listOf(HomeViews.OpenTasks, HomeViews.CompletedTasks)

    ModalDrawerSheet(drawerContainerColor = Color.White) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Views", fontSize = 26.sp, fontWeight = FontWeight.Bold)

            Column(modifier = Modifier.padding(14.dp)) {

                viewsOptions.forEach { viewsOption ->
                    Row(
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = LocalIndication.current,
                                onClick = {
                                    onSelected(viewsOption)
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Gray
                            ),
                            selected = selectedView == viewsOption,
                            onClick = null
                        )

                        Text(viewsOption.text, fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

enum class HomeViews(val text: String) {
    OpenTasks("Open Tasks"), CompletedTasks("Completed Tasks")
}
