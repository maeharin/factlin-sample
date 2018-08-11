package com.maeharin.factlin_sample.extension

import org.joda.time.DateTime

fun java.time.LocalDate.toJodaDateTime(): org.joda.time.DateTime {
    return DateTime(year, monthValue, dayOfMonth, 0, 0, 0)
}

fun java.time.LocalDateTime.toJodaDateTime(): org.joda.time.DateTime {
    return DateTime(year, monthValue, dayOfMonth, hour, minute, second)
}

