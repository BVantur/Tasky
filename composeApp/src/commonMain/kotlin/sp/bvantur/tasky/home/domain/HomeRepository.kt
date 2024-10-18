package sp.bvantur.tasky.home.domain

import kotlinx.coroutines.flow.Flow
import sp.bvantur.tasky.core.domain.CommunicationError
import sp.bvantur.tasky.core.domain.TaskyEmptyResult
import sp.bvantur.tasky.home.domain.model.AgendaItem

interface HomeRepository {
    suspend fun getTodayAgenda(time: Long): TaskyEmptyResult<CommunicationError>

    suspend fun observeAgendaItems(): Flow<List<AgendaItem>>
}
