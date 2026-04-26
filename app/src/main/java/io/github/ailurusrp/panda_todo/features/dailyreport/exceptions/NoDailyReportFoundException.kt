package io.github.ailurusrp.panda_todo.features.dailyreport.exceptions

class NoDailyReportFoundException : Exception() {
    override val message: String = "No Daily Report Found In This Date!"
}