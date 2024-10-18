package sp.bvantur.tasky.core.data

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory
import sp.bvantur.tasky.core.domain.DispatcherProvider

fun createRoomDatabase(dispatcherProvider: DispatcherProvider): TaskyDatabase {
    val dbFile = "${NSHomeDirectory()}/${TaskyDatabaseConstants.DB_FILE_NAME}"
    return Room.databaseBuilder<TaskyDatabase>(name = dbFile)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(dispatcherProvider.io)
        .build()
}
