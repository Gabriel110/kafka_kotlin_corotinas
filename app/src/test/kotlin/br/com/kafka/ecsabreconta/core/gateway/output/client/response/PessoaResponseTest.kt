package br.com.kafka.ecsabreconta.core.gateway.output.client.response

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class PessoaResponseTest {

    @Test
    fun `test constructor and properties`() {
        val nome = "João Silva"
        val cpf = "123.456.789-00"
        val dataNascimento = "01/01/1990"

        val pessoaResponse = PessoaResponse(nome, cpf, dataNascimento)

        assertEquals(nome, pessoaResponse.nome)
        assertEquals(cpf, pessoaResponse.cpf)
        assertEquals(dataNascimento, pessoaResponse.dataNascimento)
    }

    @Test
    fun `test copy method`() {
        val original = PessoaResponse("João Silva", "123.456.789-00", "01/01/1990")
        val copy = original.copy(nome = "Maria Oliveira")

        assertEquals("Maria Oliveira", copy.nome)
        assertEquals("123.456.789-00", copy.cpf)
        assertEquals("01/01/1990", copy.dataNascimento)
        assertNotEquals(original, copy)
    }

    @Test
    fun `test equals and hashCode`() {
        val pessoa1 = PessoaResponse("João Silva", "123.456.789-00", "01/01/1990")
        val pessoa2 = PessoaResponse("João Silva", "123.456.789-00", "01/01/1990")
        val pessoa3 = PessoaResponse("Maria Oliveira", "987.654.321-00", "02/02/1992")

        assertEquals(pessoa1, pessoa2)
        assertNotEquals(pessoa1, pessoa3)

        assertEquals(pessoa1.hashCode(), pessoa2.hashCode())
        assertNotEquals(pessoa1.hashCode(), pessoa3.hashCode())
    }

    @Test
    fun `test toString method`() {
        val pessoaResponse = PessoaResponse("João Silva", "123.456.789-00", "01/01/1990")
        val expectedString = "PessoaResponse(nome=João Silva, cpf=123.456.789-00, dataNascimento=01/01/1990)"

        assertEquals(expectedString, pessoaResponse.toString())
    }
}
