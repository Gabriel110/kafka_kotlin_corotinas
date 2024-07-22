package br.com.kafka.ecsabreconta.core.gateway.output.client

import br.com.kafka.ecsabreconta.core.gateway.output.client.response.TokenResponse
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Field
import java.util.UUID

interface AuthClient {
    @POST("/oauth/token")
    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    fun getToken(
        @Header("x-gcs-correlationID") correlationID: String = UUID.randomUUID().toString(),
        @Header("x-gcs-flowID") flowID: String = UUID.randomUUID().toString(),
        @Header("x-gcs-apikey") apiKey: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String = "client_credentials",
    ): Call<TokenResponse>
}