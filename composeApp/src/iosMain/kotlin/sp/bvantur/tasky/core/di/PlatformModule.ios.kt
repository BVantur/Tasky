package sp.bvantur.tasky.core.di

import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module
import sp.bvantur.tasky.core.data.TaskyDatabase
import sp.bvantur.tasky.core.data.createRoomDatabase

actual val platformModule = module {
    single {
        Darwin.create()
    }

    single<TaskyDatabase> { createRoomDatabase(get()) }
}
