package sp.bvantur.tasky.core.data

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import sp.bvantur.tasky.core.domain.DispatcherProvider

fun createRoomDatabase(context: Context, dispatcherProvider: DispatcherProvider): TaskyDatabase {
    val dbFile = context.getDatabasePath(TaskyDatabaseConstants.DB_FILE_NAME)
    return Room.databaseBuilder<TaskyDatabase>(context, dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(dispatcherProvider.io)
        .build()
}
