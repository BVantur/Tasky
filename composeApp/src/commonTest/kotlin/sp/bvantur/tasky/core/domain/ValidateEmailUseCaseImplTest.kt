package sp.bvantur.tasky.core.domain

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidateEmailUseCaseImplTest {

    @Test
    fun `GIVEN email is empty WHEN validating THEN validation fails`() {
        val email = ""
        val useCase = ValidateEmailUseCaseImpl()

        assertFalse(useCase(email))
    }

    @Test
    fun `GIVEN email has only username WHEN validating THEN validation fails`() {
        val email = "testing"
        val useCase = ValidateEmailUseCaseImpl()

        assertFalse(useCase(email))
    }

    @Test
    fun `GIVEN email only username and at sign WHEN validating THEN validation fails`() {
        val email = "testing@"
        val useCase = ValidateEmailUseCaseImpl()

        assertFalse(useCase(email))
    }

    @Test
    fun `GIVEN email is missing top level domain name and dot WHEN validating THEN validation fails`() {
        val email = "testing@gmail"
        val useCase = ValidateEmailUseCaseImpl()

        assertFalse(useCase(email))
    }

    @Test
    fun `GIVEN email is missing top level domain name WHEN validating THEN validation fails`() {
        val email = "testing@gmail."
        val useCase = ValidateEmailUseCaseImpl()

        assertFalse(useCase(email))
    }

    @Test
    fun `GIVEN email is in correct form WHEN validating THEN validation succeeds`() {
        val email = "testing@gmail.com"
        val useCase = ValidateEmailUseCaseImpl()

        assertTrue(useCase(email))
    }
}
