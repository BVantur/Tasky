package sp.bvantur.tasky.event.ui.model

import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SingleInputModelNavType : NavType<SingleInputModel>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): SingleInputModel? {
        val inputModel = bundle.getString(key) ?: return null

        return Json.decodeFromString(inputModel)
    }

    override fun parseValue(value: String): SingleInputModel {
        return Json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: SingleInputModel) {
        bundle.putString(key, Json.encodeToString(value))
    }
}
