package com.example.luggagesystemauthenticationapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableJpaRepositories
@EnableDiscoveryClient
class LuggageSystemAuthenticationApiApplication

fun main(args: Array<String>) {
    runApplication<LuggageSystemAuthenticationApiApplication>(*args)
}
