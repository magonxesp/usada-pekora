package com.usadapekora.auth.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer


@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun webHttpSecurity(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.authorizeExchange {
            it.anyExchange().permitAll()
                .and()
                .csrf().disable()
                .cors().disable()
        }

        return http.build()
    }

    @Bean
    fun corsConfigurer() = object : WebFluxConfigurer {
        override fun addCorsMappings(registry: CorsRegistry) {
            registry
                .addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
        }
    }

}
