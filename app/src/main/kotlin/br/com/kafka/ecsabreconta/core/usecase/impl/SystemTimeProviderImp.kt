package br.com.kafka.ecsabreconta.core.usecase.impl

import br.com.kafka.ecsabreconta.core.usecase.TimeProvider
import org.springframework.stereotype.Service

@Service
class SystemTimeProvider : TimeProvider {
    override fun currentTimeMillis(): Long {
        return System.currentTimeMillis()
    }
}