package br.com.kafka.ecsabreconta.core.gateway.output.client.usecase.impl

import br.com.kafka.ecsabreconta.core.gateway.output.client.AuthClient
import br.com.kafka.ecsabreconta.core.gateway.output.client.response.TokenResponse
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import retrofit2.Call
import retrofit2.Response
import kotlin.test.assertEquals

class AuthServiceImpTest {

    private val authClient: AuthClient = mock(AuthClient::class.java)
    private val call: Call<TokenResponse> = mock(Call::class.java) as Call<TokenResponse>
    private val authServiceImp = AuthServiceImp(authClient)

    @Test
    fun `test getToken success`() {
        val tokenResponse = TokenResponse(
            access_token = "abc123",
            token_type = "Bearer",
            expires_in = 3600,
            refresh_token = "refresh123",
            active = true
        )
        val response = Response.success(tokenResponse)
        `when`(call.execute()).thenReturn(response)
        `when`(authClient.getToken(
            apiKey = "d97a2a44-7dec-4dcf-bcc7-3df1d41b7dd6",
            clientSecret = "3a547506-9421-4c78-afad-ce94c54e4baa",
            clientId = "ae0a1bfa-f3e6-490a-b6d9-7a9ce4d0e551"
        )).thenReturn(call)

//        val result = authServiceImp.getToken("clientId", "clientSecret")
//
//        assertEquals(tokenResponse, result)

    }

}
