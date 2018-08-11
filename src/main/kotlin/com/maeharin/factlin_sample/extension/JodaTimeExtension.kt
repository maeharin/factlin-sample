package com.maeharin.factlin_sample.extension

import java.time.LocalDate
import java.time.LocalDateTime

fun org.joda.time.DateTime.toJavaLocalDateTime(): java.time.LocalDateTime {
    return LocalDateTime.of(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute)
}

fun org.joda.time.DateTime.toJavaLodalDate(): java.time.LocalDate {
    return LocalDate.of(year, monthOfYear, dayOfMonth)
}
