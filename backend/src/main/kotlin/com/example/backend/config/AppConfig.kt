package com.example.backend.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app.my-config")
class AppConfig {
    /**
     * The type of user repository to use.
     * Can be 'database' or 'placeholder'.
     */
    var userRepository: String? = null
}
