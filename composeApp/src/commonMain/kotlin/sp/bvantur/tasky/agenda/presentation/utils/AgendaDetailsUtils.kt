package sp.bvantur.tasky.agenda.presentation.utils

import io.ktor.util.date.getTimeMillis

object AgendaDetailsUtils {
    fun generateAgendaId(): String {
        return getTimeMillis().toString() // TODO use UUID when update to Kotlin 2.0.20
    }
}
