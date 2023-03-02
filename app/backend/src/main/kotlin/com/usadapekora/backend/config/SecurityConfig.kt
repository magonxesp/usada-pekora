package com.usadapekora.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeRequests {
                it.anyRequest()
                    .permitAll()
                    .and()
                    .csrf()
                    .disable()
                    .cors()
                    .disable()
            }

        return http.build()
    }

    @Bean
    fun corsConfigurer() = object : WebMvcConfigurer {
        override fun addCorsMappings(registry: CorsRegistry) {
            registry
                .addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
        }
    }

}
