package sp.bvantur.tasky.core.domain

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidateNameUseCaseImplTest {

    @Test
    fun `GIVEN name is empty WHEN validating THEN validation fails`() {
        val name = ""
        val useCase = ValidateNameUseCaseImpl()

        assertFalse(useCase(name))
    }

    @Test
    fun `GIVEN name has only 3 characters WHEN validating THEN validation fails`() {
        val name = "Tes"
        val useCase = ValidateNameUseCaseImpl()

        assertFalse(useCase(name))
    }

    @Test
    fun `GIVEN name has only 51 characters WHEN validating THEN validation fails`() {
        val name = "123456789012345678901234567890123456789012345678901"
        val useCase = ValidateNameUseCaseImpl()

        assertFalse(useCase(name))
    }

    @Test
    fun `GIVEN name has only 4 characters WHEN validating THEN validation succeeds`() {
        val name = "Test"
        val useCase = ValidateNameUseCaseImpl()

        assertTrue(useCase(name))
    }

    @Test
    fun `GIVEN name has only 50 characters WHEN validating THEN validation succeeds`() {
        val name = "12345678901234567890123456789012345678901234567890"
        val useCase = ValidateNameUseCaseImpl()

        assertTrue(useCase(name))
    }
}
