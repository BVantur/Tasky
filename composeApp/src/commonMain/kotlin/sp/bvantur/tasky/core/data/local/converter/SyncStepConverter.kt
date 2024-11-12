package sp.bvantur.tasky.core.data.local.converter

import androidx.room.TypeConverter
import sp.bvantur.tasky.core.data.local.SyncStep

class SyncStepConverter {
    @TypeConverter
    fun toSyncStep(value: Int): SyncStep = SyncStep.entries[value]

    @TypeConverter
    fun fromSyncStep(syncStep: SyncStep): Int = syncStep.ordinal
}
