package sp.bvantur.tasky.event.data

import sp.bvantur.tasky.event.domain.CreateEventRepository
import sp.bvantur.tasky.event.domain.model.Attendee

class CreateEventRepositoryImpl(private val remoteDataSource: CreateEventRemoteDataSource) : CreateEventRepository {
    override suspend fun getAttendee(email: String): Result<Attendee?> {
        val response = remoteDataSource.getAttendee(email)
        val exception = response.exceptionOrNull()
        if (exception != null) {
            return Result.failure(exception)
        }
        if (response.isSuccess) {
            val responseData = response.getOrNull()

            if (responseData != null && responseData.userExists) {
                // TODO store data to database
                return Result.success(
                    Attendee(
                        userId = responseData.attendee.userId,
                        name = responseData.attendee.name
                    )
                )
            }
            return Result.success(null)
        }

        return Result.failure(Exception("Unknown error"))
    }
}
