package sp.bvantur.tasky

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import org.junit.jupiter.api.Test

class FeaturesKonsistTest {

    private val core = Layer("Core", "sp.bvantur.tasky.core..")

    private val features = listOf(
        Layer("Splash", "sp.bvantur.tasky.splash.."),
        Layer("Register", "sp.bvantur.tasky.register.."),
        Layer("Login", "sp.bvantur.tasky.login.."),
        Layer("Home", "sp.bvantur.tasky.home.."),
        Layer("Event", "sp.bvantur.tasky.event..")
    )

    @Test
    fun `single features depends only on core feature`() {
        Konsist
            .scopeFromProduction("composeApp")
            .assertArchitecture {
                features.forEach { feature ->
                    feature.dependsOn(core)
                    features.filter { it != feature }.forEach {
                        feature.doesNotDependOn(it)
                    }
                }
            }
    }
}
