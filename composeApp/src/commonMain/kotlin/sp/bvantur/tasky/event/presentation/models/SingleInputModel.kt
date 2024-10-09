package sp.bvantur.tasky.event.presentation.models

import kotlinx.serialization.Serializable

@Serializable
data class SingleInputModel(val value: String? = null, val inputType: InputType)

enum class InputType {
    TITLE, DESCRIPTION;

    fun isTitle(): Boolean = this == TITLE
}
