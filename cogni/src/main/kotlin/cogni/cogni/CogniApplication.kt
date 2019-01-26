package cogni.cogni

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CogniApplication

fun main(args: Array<String>) {
	runApplication<CogniApplication>(*args)
}

