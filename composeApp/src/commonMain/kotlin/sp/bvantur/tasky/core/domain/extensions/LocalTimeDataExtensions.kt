package sp.bvantur.tasky.core.domain.extensions

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

fun LocalDateTime.getMillis(): Long = toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()

fun LocalDateTime.changeOnlyDate(dateTime: LocalDateTime?): LocalDateTime {
    dateTime ?: return this

    return LocalDateTime(
        year = dateTime.year,
        month = dateTime.month,
        dayOfMonth = dateTime.dayOfMonth,
        hour = hour,
        minute = minute,
        second = second,
        nanosecond = nanosecond
    )
}

fun LocalDateTime.changeOnlyTime(hour: Int, minutes: Int): LocalDateTime = LocalDateTime(
    year = year,
    month = month,
    dayOfMonth = dayOfMonth,
    hour = hour,
    minute = minutes,
    second = second,
    nanosecond = nanosecond
)

fun LocalDateTime.plus(minutes: Duration): LocalDateTime {
    val currentTimeZone = TimeZone.currentSystemDefault()
    return toInstant(currentTimeZone).plus(minutes).toLocalDateTime(currentTimeZone)
}

fun LocalDateTime.minus(minutes: Duration): LocalDateTime {
    val currentTimeZone = TimeZone.currentSystemDefault()
    return toInstant(currentTimeZone).minus(minutes).toLocalDateTime(currentTimeZone)
}

fun LocalDateTime.formatDate(): String {
    val month = month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
    val day = dayOfMonth
    val year = year

    return "$month $day $year"
}

fun LocalDateTime.formatTime(): String {
    val hour = hour.toString().padStart(2, '0')
    val minute = minute.toString().padStart(2, '0')
    return "$hour:$minute"
}

fun LocalDateTime.adoptToDate(currentToDate: LocalDateTime): LocalDateTime = if (this == currentToDate) {
    currentToDate.plus(30.minutes)
} else if (this > currentToDate) {
    this.plus(30.minutes)
} else {
    currentToDate
}

fun LocalDateTime.adoptFromDate(currentFromDate: LocalDateTime): LocalDateTime = if (this == currentFromDate) {
    currentFromDate.minus(30.minutes)
} else if (this < currentFromDate) {
    this.minus(30.minutes)
} else {
    currentFromDate
}
