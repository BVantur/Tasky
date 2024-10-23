package sp.bvantur.tasky.home.domain.model

data class AgendaItem(
    val id: String,
    val title: String,
    val description: String,
    val duration: String,
    val type: AgendaType
)

enum class AgendaType {
    EVENT,
    TASK,
    REMINDER
}
