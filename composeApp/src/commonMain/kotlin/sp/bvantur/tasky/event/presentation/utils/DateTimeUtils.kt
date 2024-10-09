package sp.bvantur.tasky.event.presentation.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DateTimeUtils {

    fun toLocalDateTime(millis: Long?): LocalDateTime = Instant.fromEpochMilliseconds(millis ?: 0L)
        .toLocalDateTime(TimeZone.currentSystemDefault())
}
