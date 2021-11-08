package com.tiagodeveloper.mockgenerator

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MockGeneratorApplication

	fun main(args: Array<String>) {
		SpringApplication.run(MockGeneratorApplication::class.java, *args)
	}
