package io.github.ailurusrp.panda_todo.features.dailyreport.exceptions

class MoreThanOneDailyReportFoundException: Exception() {
    override val message: String = "More Than One Daily Reports Found In This Date!"
}