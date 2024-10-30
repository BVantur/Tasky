package sp.bvantur.tasky.core.data.local

enum class SyncStep {
    NONE,
    CREATE,
    EDIT,
    DELETE;

    fun isCreate(): Boolean = this == CREATE
}
