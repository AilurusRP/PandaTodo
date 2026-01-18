package com.ailurusrp.panda_todo.features.home.data.model

import android.util.Log
import com.ailurusrp.panda_todo.common.utils.DateUtils
import com.ailurusrp.panda_todo.features.home.domain.ResetInterval
import io.realm.kotlin.types.RealmUUID
import java.time.temporal.ChronoUnit

class RecurringTask(
    override var id: RealmUUID,
    override var name: String,
    override var creationDate: Long,
    override var completed: Boolean,
    var resetInterval: String,
    override var completionDate: Long?
) : Task {

    val nextRecurrenceDate: Long
        get() {
            val creationDate = DateUtils.toZonedDateTime(this.creationDate)
            val todayDate = DateUtils.toZonedDateTime(DateUtils.getTodayDate())
            val daysSinceCreation = ChronoUnit.DAYS.between(creationDate, todayDate)
            val monthsSinceCreation = ChronoUnit.MONTHS.between(creationDate, todayDate)

            val nextRecurrenceDate = when (ResetInterval.valueOf(this.resetInterval)) {
                ResetInterval.OneDay -> creationDate.plusDays(daysSinceCreation + 1)
                ResetInterval.TwoDays -> creationDate.plusDays(daysSinceCreation + (2 - daysSinceCreation % 2))
                ResetInterval.ThreeDays -> creationDate.plusDays(daysSinceCreation + (3 - daysSinceCreation % 3))
                ResetInterval.OneWeek -> creationDate.plusDays(daysSinceCreation + (7 - daysSinceCreation % 7))
                ResetInterval.TwoWeeks -> creationDate.plusDays(daysSinceCreation + (14 - daysSinceCreation % 14))
                ResetInterval.OneMonth -> {
                    if (todayDate.dayOfMonth < creationDate.dayOfMonth) {
                        creationDate.plusMonths(monthsSinceCreation)
                            .plusDays((creationDate.dayOfMonth - todayDate.dayOfMonth).toLong())
                    } else {
                        creationDate.plusMonths(monthsSinceCreation + 1)
                    }
                }
            }

            return nextRecurrenceDate.toInstant().toEpochMilli()
        }

    val needUpdateCompletionStatus: Boolean
        get() {
            if (!completed || completionDate == null) {
                return false
            } else {
                val todayDate = DateUtils.toZonedDateTime(DateUtils.getTodayDate())
                val daysSinceCreation =
                    ChronoUnit.DAYS.between(DateUtils.toZonedDateTime(creationDate), todayDate)
                val daysBetweenCreationAndCompletion =
                    ChronoUnit.DAYS.between(
                        DateUtils.toZonedDateTime(creationDate),
                        DateUtils.toZonedDateTime(completionDate!!)
                    )

                return when (ResetInterval.valueOf(this.resetInterval)) {
                    ResetInterval.OneDay -> DateUtils.getTodayDate() != completionDate
                    ResetInterval.TwoDays -> daysSinceCreation / 2 > daysBetweenCreationAndCompletion / 2
                    ResetInterval.ThreeDays -> daysSinceCreation / 3 > daysBetweenCreationAndCompletion / 3
                    ResetInterval.OneWeek -> daysSinceCreation / 7 > daysBetweenCreationAndCompletion / 7
                    ResetInterval.TwoWeeks -> daysSinceCreation / 14 > daysBetweenCreationAndCompletion / 14

                    ResetInterval.OneMonth -> {
                        val monthsSinceCreation = ChronoUnit.MONTHS.between(
                            DateUtils.toZonedDateTime(creationDate),
                            todayDate
                        )
                        val monthsBetweenCreationAndCompletion = ChronoUnit.MONTHS.between(
                            DateUtils.toZonedDateTime(creationDate),
                            DateUtils.toZonedDateTime(completionDate!!)
                        )
                        val creationDayOfMonth = DateUtils.toZonedDateTime(creationDate).dayOfMonth
                        val completionDayOfMonth =
                            DateUtils.toZonedDateTime(completionDate!!).dayOfMonth


                        if (monthsSinceCreation - monthsBetweenCreationAndCompletion > 1) {
                            return true
                        } else if (monthsSinceCreation - monthsBetweenCreationAndCompletion == 1L) {
                            return if (completionDayOfMonth < creationDayOfMonth) {
                                true
                            } else {
                                todayDate.dayOfMonth >= creationDayOfMonth
                            }
                        } else if (monthsSinceCreation - monthsBetweenCreationAndCompletion == 0L) {
                            return if (completionDayOfMonth < creationDayOfMonth && todayDate.dayOfMonth < creationDayOfMonth) {
                                false
                            } else if (completionDayOfMonth >= creationDayOfMonth && todayDate.dayOfMonth >= creationDayOfMonth) {
                                false
                            } else {
                                true
                            }
                        } else {
                            Log.e("DATE", "monthsSinceCreation: $monthsSinceCreation")
                            Log.e(
                                "DATE",
                                "monthsBetweenCreationAndCompletion: $monthsBetweenCreationAndCompletion"
                            )
                            throw Error("`monthsSinceCreation - monthsBetweenCreationAndCompletion` must not be lower than 0!")
                        }
                    }
                }
            }
        }

    companion object {
        @JvmStatic
        fun fromRecurringTaskRealm(data: RecurringTaskRealm): RecurringTask {
            return RecurringTask(
                data.id,
                data.name,
                data.creationDate,
                data.completed,
                data.resetInterval,
                data.completionDate
            )
        }
    }
}

