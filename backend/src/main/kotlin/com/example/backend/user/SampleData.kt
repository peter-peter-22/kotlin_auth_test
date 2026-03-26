package com.example.backend.user

import com.example.backend.auth.AuthService
import com.example.backend.dto.RegisterRequest
import org.springframework.stereotype.Service

@Service
class SampleData(val authService: AuthService) {
    init {
        createSampleUsers()
    }

    fun createSampleUsers() {
        authService.register(RegisterRequest("user", "password"))
    }
}