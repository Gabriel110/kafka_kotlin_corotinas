package br.com.kafka.ecsabreconta.config

import br.com.kafka.ecsabreconta.core.gateway.output.client.AuthClient
import br.com.kafka.ecsabreconta.core.gateway.output.client.DadosPessoalClient
import br.com.kafka.ecsabreconta.core.gateway.output.client.response.TokenResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Configuration
class NetworkConfig(
    @Value("\${api.time.ms}")
    private val timeOut: Long = 10
) {
    val client = OkHttpClient()

    @Bean
    fun pessoaClient(
        @Value("\${api.base-url.pessoa}") baseUrl: String
    ): DadosPessoalClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
            }).build()
        val gson = GsonBuilder().create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(DadosPessoalClient::class.java)
    }

    @Bean
    fun authClientBean(
        @Value("\${api.base-url.pessoa}") baseUrl: String
    ): AuthClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
            }).build()
        val gson = GsonBuilder().create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(AuthClient::class.java)
    }
}