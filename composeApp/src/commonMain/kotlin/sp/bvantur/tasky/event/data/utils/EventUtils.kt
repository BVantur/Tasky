package sp.bvantur.tasky.event.data.utils

import io.ktor.util.date.getTimeMillis

object EventUtils {
    fun generateEventId(): String {
        return getTimeMillis().toString() // TODO use UUID when update to Kotlin 2.0.20
    }
}
