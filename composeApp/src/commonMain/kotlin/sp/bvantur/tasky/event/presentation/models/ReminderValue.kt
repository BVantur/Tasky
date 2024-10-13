package sp.bvantur.tasky.event.presentation.models

import org.jetbrains.compose.resources.StringResource
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.one_day_before
import tasky.composeapp.generated.resources.one_hour_before
import tasky.composeapp.generated.resources.six_hour_before
import tasky.composeapp.generated.resources.ten_minutes_before
import tasky.composeapp.generated.resources.thirty_minutes_before

enum class ReminderValue(val stringRes: StringResource) {
    TEN_MINUTES(Res.string.ten_minutes_before),
    THIRTY_MINUTES(Res.string.thirty_minutes_before),
    ONE_HOUR(Res.string.one_hour_before),
    SIX_HOUR(Res.string.six_hour_before),
    ONE_DAY(Res.string.one_day_before)
}
