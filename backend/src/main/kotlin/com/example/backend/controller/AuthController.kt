package com.example.backend.controller

import com.example.backend.dto.AuthResponse
import com.example.backend.dto.LoginRequest
import com.example.backend.dto.RegisterRequest
import com.example.backend.service.AuthService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/login")
    fun login(
        @RequestBody req: LoginRequest,
        request: HttpServletRequest
    ): AuthResponse {
        return authService.login(req,request)
    }

    @PostMapping("/register")
    fun register(
        @RequestBody req: RegisterRequest,
        request: HttpServletRequest
    ): AuthResponse {
        return authService.register(req,request)
    }
}