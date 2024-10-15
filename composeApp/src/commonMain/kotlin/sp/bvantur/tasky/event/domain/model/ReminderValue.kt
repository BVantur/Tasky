package sp.bvantur.tasky.event.domain.model

import org.jetbrains.compose.resources.StringResource
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.one_day_before
import tasky.composeapp.generated.resources.one_hour_before
import tasky.composeapp.generated.resources.six_hour_before
import tasky.composeapp.generated.resources.ten_minutes_before
import tasky.composeapp.generated.resources.thirty_minutes_before

@Suppress("MagicNumber")
enum class ReminderValue(val stringRes: StringResource, private val inMillis: Long) {
    TEN_MINUTES(Res.string.ten_minutes_before, 600000L),
    THIRTY_MINUTES(Res.string.thirty_minutes_before, 1800000L),
    ONE_HOUR(Res.string.one_hour_before, 3600000L),
    SIX_HOUR(Res.string.six_hour_before, 21600000L),
    ONE_DAY(Res.string.one_day_before, 86400000L);

    fun toMillis(fromTimestamp: Long): Long = fromTimestamp - inMillis
}
