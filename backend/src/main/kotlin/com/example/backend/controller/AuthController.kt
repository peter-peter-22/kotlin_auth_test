package com.example.backend.controller

import com.example.backend.dto.AuthResponse
import com.example.backend.dto.LoginRequest
import com.example.backend.dto.RegisterRequest
import com.example.backend.auth.AuthService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/login")
    fun login(
        @RequestBody data: LoginRequest,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): AuthResponse {
        return authService.login(data,request,response)
    }

    @PostMapping("/register")
    fun register(
        @RequestBody data: RegisterRequest
    ): AuthResponse {
        return authService.register(data)
    }
}