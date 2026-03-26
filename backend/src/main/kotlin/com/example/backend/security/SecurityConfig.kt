package com.example.backend.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.context.DelegatingSecurityContextRepository
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityContextRepository(): SecurityContextRepository {
        return DelegatingSecurityContextRepository(
            RequestAttributeSecurityContextRepository(),
            HttpSessionSecurityContextRepository()
        )
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        securityContextRepository: SecurityContextRepository
    ): SecurityFilterChain {
        http
            .cors { it.configurationSource(corsConfigurationSource()) }
            .csrf { it.disable() }
            // fix the blocked rendering of the h2 console
            .headers { headers ->
                headers.frameOptions { frameOptions ->
                    frameOptions.sameOrigin()
                }
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/api/optional").authenticated()
                    .requestMatchers("/api/private").authenticated()
                    .anyRequest().permitAll()
            }
            .formLogin { it.disable() }
            .logout { it.logoutUrl("/api/logout") }
            .securityContext {
                it.securityContextRepository(securityContextRepository)
                it.requireExplicitSave(true) // default
            }
            .sessionManagement { sessionConfigurer ->
                sessionConfigurer
                    // default
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            }

        return http.build()
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = mutableListOf("http://localhost:5173")
        configuration.allowedMethods = mutableListOf("*")
        configuration.allowedHeaders = mutableListOf("*")
        configuration.exposedHeaders = mutableListOf("*")
        configuration.maxAge = 0
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}