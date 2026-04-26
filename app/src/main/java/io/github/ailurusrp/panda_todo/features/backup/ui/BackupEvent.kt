package io.github.ailurusrp.panda_todo.features.backup.ui

sealed interface BackupEvent {
    data class Success(val message: String) : BackupEvent
    data class Error(val message: String) : BackupEvent
}
