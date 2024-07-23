package br.com.kafka.ecsabreconta.core.gateway.output.client.response

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class TokenResponseTest {

    @Test
    fun `test constructor and properties`() {
        val accessToken = "abc123"
        val tokenType = "Bearer"
        val expiresIn = 3600
        val refreshToken = "refresh123"
        val active = true

        val tokenResponse = TokenResponse(accessToken, tokenType, expiresIn, refreshToken, active)

        assertEquals(accessToken, tokenResponse.access_token)
        assertEquals(tokenType, tokenResponse.token_type)
        assertEquals(expiresIn, tokenResponse.expires_in)
        assertEquals(refreshToken, tokenResponse.refresh_token)
        assertEquals(active, tokenResponse.active)
    }

    @Test
    fun `test copy method`() {
        val original = TokenResponse("abc123", "Bearer", 3600, "refresh123", true)
        val copy = original.copy(access_token = "xyz456", expires_in = 7200)

        assertEquals("xyz456", copy.access_token)
        assertEquals("Bearer", copy.token_type)
        assertEquals(7200, copy.expires_in)
        assertEquals("refresh123", copy.refresh_token)
        assertEquals(true, copy.active)
        assertNotEquals(original, copy)
    }

    @Test
    fun `test equals and hashCode`() {
        val token1 = TokenResponse("abc123", "Bearer", 3600, "refresh123", true)
        val token2 = TokenResponse("abc123", "Bearer", 3600, "refresh123", true)
        val token3 = TokenResponse("xyz456", "Bearer", 7200, "refresh456", false)

        assertEquals(token1, token2)
        assertNotEquals(token1, token3)

        assertEquals(token1.hashCode(), token2.hashCode())
        assertNotEquals(token1.hashCode(), token3.hashCode())
    }

    @Test
    fun `test toString method`() {
        val tokenResponse = TokenResponse("abc123", "Bearer", 3600, "refresh123", true)
        val expectedString = "TokenResponse(access_token=abc123, token_type=Bearer, expires_in=3600, refresh_token=refresh123, active=true)"

        assertEquals(expectedString, tokenResponse.toString())
    }
}
