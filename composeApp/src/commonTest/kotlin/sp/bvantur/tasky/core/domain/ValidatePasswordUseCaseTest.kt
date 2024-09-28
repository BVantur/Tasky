package sp.bvantur.tasky.core.domain

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidatePasswordUseCaseTest {

    @Test
    fun `GIVEN password is empty WHEN validating THEN validation fails`() {
        val password = ""
        val useCase = ValidatePasswordUseCase()

        assertFalse(useCase(password))
    }

    @Test
    fun `GIVEN password has 9 lower cased letters WHEN validating THEN validation fails`() {
        val password = "testingit"
        val useCase = ValidatePasswordUseCase()

        assertFalse(useCase(password))
    }

    @Test
    fun `GIVEN password has 1 upper cased letter and 8 lower cased letters WHEN validating THEN validation fails`() {
        val password = "Testingit"
        val useCase = ValidatePasswordUseCase()

        assertFalse(useCase(password))
    }

    @Test
    fun `GIVEN password has 8 lower cased letters and 1 digit WHEN validating THEN validation fails`() {
        val password = "testingi1"
        val useCase = ValidatePasswordUseCase()

        assertFalse(useCase(password))
    }

    @Test
    fun `GIVEN password has 1 upper cased letter 7 lower cased letters and 1 digit WHEN validating THEN validation succeeds`() {
        val password = "Testingit1"
        val useCase = ValidatePasswordUseCase()

        assertTrue(useCase(password))
    }
}
