package sp.bvantur.tasky.core.data.local

enum class SyncStep {
    FULL_SYNCED,
    CREATE,
    EDIT,
    DELETE;

    fun isCreate(): Boolean = this == CREATE
}
