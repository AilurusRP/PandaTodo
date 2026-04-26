package io.github.ailurusrp.panda_todo.features.backup.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.ailurusrp.panda_todo.features.backup.data.BackupRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.InputStream
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
            } catch (err: Exception) {
                _event.emit(BackupEvent.Error("Export Failed: ${err.localizedMessage}"))
            }
        }
    }

    fun importBackup(inputStream: InputStream) {
        viewModelScope.launch {
            try {
                backupRepository.importBackup(inputStream)
                _event.emit(BackupEvent.Success("Tasks Successfully Imported!"))
            } catch (err: Exception) {
                _event.emit(BackupEvent.Error("Import Failed: ${err.localizedMessage}"))
            }
        }
    }
}