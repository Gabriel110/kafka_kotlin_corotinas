package br.com.kafka.ecsabreconta.shared.exception

class DadosClienteException(message: String, statusCode: Int) : RuntimeException(
    "StatusCode: $statusCode, Body: $message"
)