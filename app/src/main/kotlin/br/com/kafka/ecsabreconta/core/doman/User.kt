package br.com.kafka.ecsabreconta.core.doman

import org.apache.avro.Schema
import org.apache.avro.specific.SpecificRecordBase

data class User(
    var nome: String,
    var cpf: String,
    var data_nascimento: String
) : SpecificRecordBase() {
    companion object {
        val SCHEMA: Schema = Schema.Parser().parse("""
        {
          "type": "record",
          "name": "User",
          "fields": [
            {"name": "nome", "type": "string"},
            {"name": "cpf", "type": "string"},
            {"name": "data_nascimento", "type": "string"}
          ]
        }
        """)
    }

    override fun getSchema(): Schema = SCHEMA

    override fun get(field: Int): Any? {
        return when (field) {
            0 -> nome
            1 -> cpf
            2 -> data_nascimento
            else -> null
        }
    }

    override fun put(field: Int, value: Any?) {
        when (field) {
            0 -> nome = value as String
            1 -> cpf = value as String
            2 -> data_nascimento = value as String
        }
    }
}