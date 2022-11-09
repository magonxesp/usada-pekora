package com.usadapekora.context.shared.domain

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class DateTimeUtils {
    companion object {
        const val dateTimeFormat = "yyyy-MM-dd HH:mm:ss"

        fun fromFormat(date: String, format: String): ZonedDateTime {
            val formatter = DateTimeFormatter.ofPattern(format)
            return formatter.parse(date) as ZonedDateTime
        }

        fun fromISO8061(date: String): ZonedDateTime = ZonedDateTime.parse(date)

        fun format(date: ZonedDateTime): String {
            val formatter = DateTimeFormatter.ofPattern(dateTimeFormat)
            return formatter.format(date)
        }
    }
}
