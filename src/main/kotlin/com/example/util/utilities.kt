package com.example.util

import org.joda.time.DateTime
import java.time.LocalDate


/**
 * Convert {@link org.joda.time.DateTime} to {@link java.time.LocalDate}
 */
fun DateTime.toJavaLocalDate(): LocalDate {
    return LocalDate.of(this.year, this.monthOfYear, this.dayOfMonth)
}

/**
 * Convert {@link java.time.LocalDate} to {@link org.joda.time.DateTime}
 */
fun LocalDate.toDate(default: DateTime = org.joda.time.DateTime(1900,1,1,0,0,0)): DateTime{
    return DateTime(this.year, this.monthValue, this.dayOfMonth, 0,0,0)
}

/**
 * Convert {@link java.time.LocalDate} to {@link org.joda.time.LocalDate}
 *
 *
 *
 */
fun LocalDate.toJodaLocalDate() : LocalDate{
    return LocalDate.of( year,  month, dayOfMonth)
}

