package sp.bvantur.tasky

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.declaration.KoFunctionDeclaration
import com.lemonappdev.konsist.api.declaration.KoPropertyDeclaration
import com.lemonappdev.konsist.api.ext.list.indexOfFirstInstance
import com.lemonappdev.konsist.api.ext.list.indexOfLastInstance
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test

// Run tests with command ./gradlew :konsistTest:jvmTest

class ArchitectureKonsistTest {

    @Test
    fun `clean architecture layers have correct dependencies`() {
        Konsist
            .scopeFromProduction("composeApp")
            .assertArchitecture {
                val domain = Layer("Domain", "..domain..")
                val presentation = Layer("Presentation", "..presentation..")
                val data = Layer("Data", "..data..")

                domain.dependsOnNothing()
                presentation.dependsOn(domain)
                data.dependsOn(domain)
            }
    }

    @Test
    fun `properties are declared before functions`() {
        Konsist
            .scopeFromProject()
            .classes()
            .assertTrue {
                val lastKoPropertyDeclarationIndex = it
                    .declarations(includeNested = false, includeLocal = false)
                    .indexOfLastInstance<KoPropertyDeclaration>()
                val firstKoFunctionDeclarationIndex = it
                    .declarations(includeNested = false, includeLocal = false)
                    .indexOfFirstInstance<KoFunctionDeclaration>()
                if (lastKoPropertyDeclarationIndex != -1 && firstKoFunctionDeclarationIndex != -1) {
                    lastKoPropertyDeclarationIndex < firstKoFunctionDeclarationIndex
                } else {
                    true
                }
            }
    }

    @Test
    fun `classes with 'UseCase' suffix should reside in 'domain' package`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue { it.resideInPackage("..domain..") }
    }

    @Test
    fun `'Repository' classes should reside in 'data' package`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("Repository")
            .assertTrue { it.resideInPackage("..data..") }
    }
}
