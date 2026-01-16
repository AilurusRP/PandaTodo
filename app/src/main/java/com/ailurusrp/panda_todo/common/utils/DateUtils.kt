package com.ailurusrp.panda_todo.common.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class DateUtils {
    companion object {
        @JvmStatic
        fun getTodayDate(): Long {
            val zone: ZoneId = ZoneId.systemDefault()
            return LocalDate.now(zone).atStartOfDay(zone).toInstant().toEpochMilli()
        }

        @JvmStatic
        fun toZonedDateTime(date: Long): ZonedDateTime {
            val zone: ZoneId = ZoneId.systemDefault()
            return Instant.ofEpochMilli(date).atZone(zone)
        }
    }
}