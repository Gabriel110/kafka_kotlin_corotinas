package br.com.kafka.ecsabreconta.config

import br.com.kafka.ecsabreconta.core.gateway.output.client.DadosPessoalClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Configuration
class NetworkConfig {

    @Bean
    fun pessoaClient(
        @Value("\${api.base-url.pessoa}") baseUrl: String
    ): DadosPessoalClient {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DadosPessoalClient::class.java)
    }
}