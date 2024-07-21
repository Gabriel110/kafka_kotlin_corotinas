package br.com.kafka.ecsabreconta.core.gateway.output.client.response

data class PessoaResponse(
    val nome:String,
    val cpf:String,
    val dataNascimento:String
)
