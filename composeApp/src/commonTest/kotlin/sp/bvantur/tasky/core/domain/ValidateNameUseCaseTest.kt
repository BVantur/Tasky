package sp.bvantur.tasky.core.domain

import sp.bvantur.tasky.register.domain.ValidateNameUseCase
import sp.bvantur.tasky.register.domain.ValidateNameUseCase.Companion.MAX_NAME_LENGTH
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidateNameUseCaseTest {

    @Test
    fun `GIVEN name is empty WHEN validating THEN validation fails`() {
        val name = ""
        val useCase = ValidateNameUseCase()

        assertFalse(useCase(name))
    }

    @Test
    fun `GIVEN name has only 3 characters WHEN validating THEN validation fails`() {
        val name = "Tes"
        val useCase = ValidateNameUseCase()

        assertFalse(useCase(name))
    }

    @Test
    fun `GIVEN name has only 51 characters WHEN validating THEN validation fails`() {
        val name = buildString {
            repeat(MAX_NAME_LENGTH) {
                append("1")
            }
            append("1")
        }
        val useCase = ValidateNameUseCase()

        assertFalse(useCase(name))
    }

    @Test
    fun `GIVEN name has only 4 characters WHEN validating THEN validation succeeds`() {
        val name = "Test"
        val useCase = ValidateNameUseCase()

        assertTrue(useCase(name))
    }

    @Test
    fun `GIVEN name has only 50 characters WHEN validating THEN validation succeeds`() {
        val name = buildString {
            repeat(MAX_NAME_LENGTH) {
                append("1")
            }
        }
        val useCase = ValidateNameUseCase()

        assertTrue(useCase(name))
    }
}
