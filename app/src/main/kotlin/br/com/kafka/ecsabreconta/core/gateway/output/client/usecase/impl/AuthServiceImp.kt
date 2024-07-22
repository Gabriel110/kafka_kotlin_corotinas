package br.com.kafka.ecsabreconta.core.gateway.output.client.usecase.impl

import br.com.kafka.ecsabreconta.core.gateway.output.client.AuthClient
import br.com.kafka.ecsabreconta.core.gateway.output.client.response.TokenResponse
import br.com.kafka.ecsabreconta.shared.exception.AuthClienteException
import org.springframework.stereotype.Service
import retrofit2.Response

@Service
class AuthServiceImp(
    private val authClient: AuthClient
) {
    fun getToken(clientId: String, clientSercret: String): TokenResponse? {
        val response = authClient.getToken(
            clientId = clientId,
            clientSecret = clientSercret,
            apiKey = "d97a2a44-7dec-4dcf-bcc7-3df1d41b7dd6"
        ).execute()
        return response.checkSuccess().body()
    }

    @Throws(AuthClienteException::class)
    private fun <T> Response<T>.checkSuccess(): Response<T> {
        if (!isSuccessful) {
            val errorBody = errorBody()?.string()
            throw AuthClienteException(code(), message(), errorBody)
        }
        return this
    }
}