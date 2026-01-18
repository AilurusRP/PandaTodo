package com.ailurusrp.panda_todo.features.home.ui

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FilterMenuButton(
    selectedFilterOption: FilterMenuOptions,
    onSelected: (FilterMenuOptions) -> Unit
) {
    val filterMenuExpanded = remember { mutableStateOf(false) }

    val filterMenuOptions = listOf(FilterMenuOptions.OpenTasks, FilterMenuOptions.CompletedTasks)

    Box {
        IconButton(
            onClick = { filterMenuExpanded.value = true },
        ) {
            Icon(
                Icons.Default.MoreHoriz,
                contentDescription = "Add Tasks",
                modifier = Modifier.size(26.dp)
            )
        }

        DropdownMenu(
            expanded = filterMenuExpanded.value,
            onDismissRequest = { filterMenuExpanded.value = false },
            modifier = Modifier.Companion.background(color = Color.Companion.White)
        ) {
            filterMenuOptions.forEach { filterMenuOption ->
                DropdownMenuItem(
                    onClick = {},
                    text = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = LocalIndication.current,
                                    onClick = {
                                        onSelected(filterMenuOption)
                                        filterMenuExpanded.value = false
                                    }
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.Gray
                                ),
                                selected = selectedFilterOption == filterMenuOption,
                                onClick = {}
                            )

                            Text(filterMenuOption.text)
                        }
                    },
                )
            }
        }
    }
}

enum class FilterMenuOptions(val text: String) {
    OpenTasks("Open Tasks"), CompletedTasks("Completed Tasks")
}
