package com.ailurusrp.panda_todo.features.dailyreport.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ailurusrp.panda_todo.features.dailyreport.data.model.DailyReportRealm
import com.ailurusrp.panda_todo.features.dailyreport.data.DailyReportRepository
import com.ailurusrp.panda_todo.features.dailyreport.data.dailyReportDatabaseConfig
import com.ailurusrp.panda_todo.features.dailyreport.data.model.DailyReport
import com.ailurusrp.panda_todo.features.dailyreport.exceptions.NoDailyReportFoundException
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.launch

class DailyReportViewModel(private val repository: DailyReportRepository) : ViewModel() {
    fun getAllDailyReports(): List<DailyReport> {
        return repository.getAllDailyReports()
    }

    fun getDailyReportByDate(date: String): DailyReportRealm {
        return getDailyReportByDate(date)
    }

    fun addNewCompletedTaskToDailyReportOrAddDailyReport(
        date: String,
        newCompletedTask: String
    ) {
        viewModelScope.launch {
            try {
                repository.addNewCompletedTaskToDailyReport(date, newCompletedTask)
            } catch (_: NoDailyReportFoundException) {
                repository.addDailyReport(DailyReportRealm().apply {
                    creationDate = date
                    completedTasks = realmListOf(newCompletedTask)
                })
            }
        }
    }

    fun deleteCompletedTaskFromDailyReport(date: String, newCompletedTask: String) {
        viewModelScope.launch {
            repository.deleteCompletedTaskFromDailyReport(
                date,
                newCompletedTask
            )
        }
    }
}

class DailyReportViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val realm = Realm.open(dailyReportDatabaseConfig)
        val repository = DailyReportRepository(realm)
        @Suppress("UNCHECKED_CAST")
        return DailyReportViewModel(repository) as T
    }
}