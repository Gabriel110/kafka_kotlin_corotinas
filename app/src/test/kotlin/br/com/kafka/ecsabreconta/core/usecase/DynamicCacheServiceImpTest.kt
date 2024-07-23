package br.com.kafka.ecsabreconta.core.usecase

import br.com.kafka.ecsabreconta.core.doman.CacheEntry
import br.com.kafka.ecsabreconta.core.gateway.output.client.response.TokenResponse
import br.com.kafka.ecsabreconta.core.gateway.output.client.usecase.impl.AuthServiceImp
import br.com.kafka.ecsabreconta.core.usecase.impl.DynamicCacheServiceImp
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.Cache
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class DynamicCacheServiceImpTest {

    @Mock
    lateinit var authServiceImp: AuthServiceImp

    @Mock
    lateinit var timeProvider: TimeProvider

    @InjectMocks
    lateinit var dynamicCacheServiceImp: DynamicCacheServiceImp

    private lateinit var cache: Cache<String, CacheEntry>

    val newToken = TokenResponse(
        access_token = "newJwtToken",
        expires_in = 300,
        active = true,
        refresh_token = "newJwtToken",
        token_type = "Beare"
    )

    @BeforeEach
    fun setUp() {
        cache = Caffeine.newBuilder().maximumSize(100).build()
        dynamicCacheServiceImp = DynamicCacheServiceImp(authServiceImp, timeProvider)
    }

    @Test
    fun `should return null if token not present and auth service fails`() {
        `when`(authServiceImp.getToken(anyString(), anyString())).thenReturn(null)

        val result = dynamicCacheServiceImp.getJwt("testAccessToken", "clientId", "clientSecret")

        assertNull(result)
        verify(authServiceImp).getToken("clientId", "clientSecret")
    }

    @Test
    fun `should return JWT from cache if token is present and not expired`() {
        val currentTime = 1625158800000L // Simula um tempo específico
        `when`(timeProvider.currentTimeMillis()).thenReturn(currentTime)

        val cacheEntry = CacheEntry("jwtToken", currentTime / 1000 + 1000)

        val cacheField = DynamicCacheServiceImp::class.java.getDeclaredField("cache")
        cacheField.isAccessible = true
        val cache = cacheField.get(dynamicCacheServiceImp) as Cache<String, CacheEntry>
        cache.put("testAccessToken", cacheEntry)

        val result = dynamicCacheServiceImp.getJwt("testAccessToken", "clientId", "clientSecret")

        assertEquals("jwtToken", result)
    }



    @Test
    fun `should fetch new token and put in cache if token not present in cache`() {
        val currentTime = 1625158800000L // Simula um tempo específico
        `when`(timeProvider.currentTimeMillis()).thenReturn(currentTime)

        `when`(authServiceImp.getToken("clientId", "clientSecret")).thenReturn(newToken)

        val result = dynamicCacheServiceImp.getJwt("testAccessToken", "clientId", "clientSecret")

        assertEquals("newJwtToken", result)

        val cacheField = dynamicCacheServiceImp::class.java.getDeclaredField("cache")
        cacheField.isAccessible = true
        val cache = cacheField.get(dynamicCacheServiceImp) as Cache<String, CacheEntry>
        assertNotNull(cache.getIfPresent("testAccessToken"))
    }
}
