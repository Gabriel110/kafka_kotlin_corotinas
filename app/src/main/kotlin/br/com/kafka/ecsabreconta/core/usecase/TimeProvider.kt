package br.com.kafka.ecsabreconta.core.usecase

interface TimeProvider {
    fun currentTimeMillis(): Long
}