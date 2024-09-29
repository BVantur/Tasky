package sp.bvantur.tasky

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test

class UseCaseKonsistTest {

    @Test
    fun `classes with 'UseCase' suffix should reside in 'domain' package`() {
        Konsist
            .scopeFromProject()
            .interfaces()
            .withNameEndingWith("UseCase")
            .assertTrue { it.resideInPackage("..domain..") }
    }

    @Test
    fun `classes with 'UseCase' suffix should have single 'public operator' method named 'invoke'`() {
        Konsist
            .scopeFromProject()
            .interfaces()
            .withNameEndingWith("UseCase")
            .assertTrue {
                val hasSingleInvokeOperatorMethod = it.hasFunction { function ->
                    function.name == "invoke" && function.hasPublicOrDefaultModifier && function.hasOperatorModifier
                }

                hasSingleInvokeOperatorMethod && it.countFunctions { item -> item.hasPublicOrDefaultModifier } == 1
            }
    }

    @Test
    fun `every UseCase class has test`() {
        val allowedUseCasesWithoutTests = listOf(
            "RegisterUserUseCase" // TODO handle this after the mentorship
        )
        Konsist
            .scopeFromProduction()
            .classes()
            .withNameEndingWith("UseCase")
            .filter {
                !allowedUseCasesWithoutTests.contains(it.name)
            }
            .assertTrue {
                it.hasTest()
            }
    }
}
