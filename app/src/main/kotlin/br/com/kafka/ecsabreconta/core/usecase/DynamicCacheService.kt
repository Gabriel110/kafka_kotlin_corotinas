package br.com.kafka.ecsabreconta.core.usecase


import br.com.kafka.ecsabreconta.core.doman.CacheEntry
import br.com.kafka.ecsabreconta.core.gateway.output.client.usecase.impl.AuthServiceImp
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.Cache
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit


@Service
class DynamicCacheService(
    private val authServiceImp: AuthServiceImp
) {

    private val cache: Cache<String, CacheEntry> = Caffeine.newBuilder()
        .maximumSize(100)
        .build()

    private fun putJwt(accessToken: String, jwt: String, duration: Long) {
        val unit = TimeUnit.SECONDS
        val expireAt = (System.currentTimeMillis() / 1000) + unit.toSeconds(duration)
        cache.put(accessToken, CacheEntry(jwt, expireAt))
    }

    fun getJwt(accessToken: String, clientId: String, clientSecret: String): String? {
        val entry = cache.getIfPresent(accessToken)
        if (entry == null) {
            getTokenAnSetInCache(accessToken, clientId, clientSecret)
            println(cache.getIfPresent(accessToken)?.expireAt)
            return cache.getIfPresent(accessToken)?.value
        }
        return if (System.currentTimeMillis() < entry.expireAt) {
            entry.value
        } else {
            cache.invalidate(accessToken)
            getTokenAnSetInCache(accessToken, clientId, clientSecret)
            println(cache.getIfPresent(accessToken)?.expireAt)
            cache.getIfPresent(accessToken)?.value
        }
    }

    private fun getTokenAnSetInCache(accessToken: String, clientId:String, clientSecret:String){
        val response = authServiceImp.getToken(clientId, clientSecret)
        response?.let {
            putJwt(
                accessToken = accessToken,
                jwt = it.access_token,
                duration = (it.expires_in * 0.8).toLong()
            )
        }
    }
}