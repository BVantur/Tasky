package sp.bvantur.tasky.home.data.mappers

import sp.bvantur.tasky.agenda.domain.model.AgendaType
import sp.bvantur.tasky.core.data.local.EventEntity
import sp.bvantur.tasky.core.data.remote.EventResponse
import sp.bvantur.tasky.home.domain.model.AgendaItem

fun EventResponse.asEventEntity(): EventEntity = EventEntity(
    id = id,
    title = title,
    description = description,
    from = from,
    to = to,
    remindAt = remindAt,
    host = host,
    isUserEventCreator = isUserEventCreator,
)

fun EventEntity.asAgendaItem(): AgendaItem = AgendaItem(
    id = id,
    title = title,
    description = description,
    duration = "duration", // TODO fix this
    type = AgendaType.EVENT // TODO fix this
)
