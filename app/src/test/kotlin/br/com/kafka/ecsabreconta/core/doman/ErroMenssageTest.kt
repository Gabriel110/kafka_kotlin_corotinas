package br.com.kafka.ecsabreconta.core.doman

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ErroMenssageTest {

    @Test
    fun `test constructor and properties`() {
        val error = "404"
        val message = "Not Found"

        val erroMenssage = ErroMenssage(error, message)

        assertEquals(error, erroMenssage.error)
        assertEquals(message, erroMenssage.message)
    }

    @Test
    fun `test copy method`() {
        val original = ErroMenssage("404", "Not Found")
        val copy = original.copy(error = "500")

        assertEquals("500", copy.error)
        assertEquals("Not Found", copy.message)
        assertNotEquals(original, copy)
    }

    @Test
    fun `test equals and hashCode`() {
        val erroMenssage1 = ErroMenssage("404", "Not Found")
        val erroMenssage2 = ErroMenssage("404", "Not Found")
        val erroMenssage3 = ErroMenssage("500", "Server Error")

        assertEquals(erroMenssage1, erroMenssage2)
        assertNotEquals(erroMenssage1, erroMenssage3)

        assertEquals(erroMenssage1.hashCode(), erroMenssage2.hashCode())
        assertNotEquals(erroMenssage1.hashCode(), erroMenssage3.hashCode())
    }

    @Test
    fun `test toString method`() {
        val erroMenssage = ErroMenssage("404", "Not Found")
        val expectedString = "ErroMenssage(error=404, message=Not Found)"

        assertEquals(expectedString, erroMenssage.toString())
    }
}
