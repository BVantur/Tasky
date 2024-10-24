package sp.bvantur.tasky.home.domain

import kotlinx.coroutines.flow.Flow
import sp.bvantur.tasky.core.domain.TaskyEmptyResult
import sp.bvantur.tasky.core.domain.TaskyError
import sp.bvantur.tasky.home.domain.model.AgendaItem

interface HomeRepository {
    suspend fun getAgendaForTheDay(time: Long): TaskyEmptyResult<TaskyError>

    suspend fun observeAgendaItems(): Flow<List<AgendaItem>>

    suspend fun syncPendingAgendaItems()
}
