package br.com.kafka.ecsabreconta

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EcsabrecontaApplication

fun main(args: Array<String>) {
	runApplication<EcsabrecontaApplication>(*args)
}
