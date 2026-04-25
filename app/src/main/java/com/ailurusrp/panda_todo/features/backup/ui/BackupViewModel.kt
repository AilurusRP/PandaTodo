package com.ailurusrp.panda_todo.features.backup.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailurusrp.panda_todo.features.backup.data.BackupRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.OutputStream

class BackupViewModel(
    private val backupRepository: BackupRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<BackupEvent>(extraBufferCapacity = 1)
    val event: SharedFlow<BackupEvent> = _event.asSharedFlow()

    fun exportBackup(outputStream: OutputStream) {
        viewModelScope.launch {
            try {
                backupRepository.exportBackup(outputStream)
                _event.emit(BackupEvent.Success("Tasks Successfully Exported!"))
            } catch (e: Exception) {
                _event.emit(BackupEvent.Error("Export Failed: ${e.localizedMessage}"))
            }
        }
    }
}