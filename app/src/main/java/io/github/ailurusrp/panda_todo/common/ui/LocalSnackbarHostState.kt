package io.github.ailurusrp.panda_todo.common.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf

val LocalSnackbarHostState = compositionLocalOf { SnackbarHostState() }