package sp.bvantur.tasky.event.presentation.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import sp.bvantur.tasky.core.domain.extensions.getMillis
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

object DateTimeUtils {

    fun toLocalDateTime(millis: Long): LocalDateTime = Instant.fromEpochMilliseconds(millis)
        .toLocalDateTime(TimeZone.currentSystemDefault())

    fun getCurrentLocalDateTime(addDuration: Duration = 0.minutes): LocalDateTime =
        Clock.System.now().plus(addDuration).toLocalDateTime(TimeZone.currentSystemDefault())

    fun getCurrentLocalDateTimeInMillis(): Long = getCurrentLocalDateTime().getMillis()
}
