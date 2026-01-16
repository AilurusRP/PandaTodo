package com.ailurusrp.panda_todo.common.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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

        @JvmStatic
        fun toLong(date: LocalDateTime): Long {
            val zone: ZoneId = ZoneId.systemDefault()
            return date.atZone(zone).toInstant().toEpochMilli()
        }

        @JvmStatic
        fun format(date: Long): String {
            return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(date),
                ZoneId.systemDefault()
            ).format(DateTimeFormatter.ISO_LOCAL_DATE)
        }
    }
}