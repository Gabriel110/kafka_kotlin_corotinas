package br.com.kafka.ecsabreconta.shared.exception

class AuthClienteException(val statusCode: Int, message: String, val errorBody: String?) :
    RuntimeException("API Error: StatusCode: $statusCode, Error: $message, Body: $errorBody") {
    override fun toString(): String {
        return "statusCode=$statusCode, message=$message, errorBody=$errorBody"
    }
}