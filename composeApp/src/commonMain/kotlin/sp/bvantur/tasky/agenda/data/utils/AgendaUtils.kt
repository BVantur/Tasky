package sp.bvantur.tasky.agenda.data.utils

import io.ktor.util.date.getTimeMillis
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object AgendaUtils {
    fun generateAgendaId(): String {
        return getTimeMillis().toString() // TODO use UUID when update to Kotlin 2.0.20
    }

    fun toLocalDateTime(millis: Long): LocalDateTime =
        Instant.fromEpochMilliseconds(millis).toLocalDateTime(TimeZone.currentSystemDefault())
}
