package br.com.kafka.ecsabreconta.config

import br.com.kafka.ecsabreconta.core.gateway.output.client.AuthClient
import br.com.kafka.ecsabreconta.core.gateway.output.client.DadosPessoalClient
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.util.ReflectionTestUtils

@ExtendWith(MockitoExtension::class)
class NetworkConfigTest {

    @InjectMocks
    private lateinit var networkConfig: NetworkConfig

    @BeforeEach
    fun setUp() {
        ReflectionTestUtils.setField(networkConfig, "timeOut", 10L)
    }

    @Test
    fun testPessoaClient() {
        val baseUrl = "https://example.com"
        val pessoaClient: DadosPessoalClient = networkConfig.pessoaClient(baseUrl)
        assertNotNull(pessoaClient)
    }

    @Test
    fun testAuthClientBean() {
        val baseUrl = "https://example.com"
        val authClient: AuthClient = networkConfig.authClientBean(baseUrl)
        assertNotNull(authClient)
    }
}
