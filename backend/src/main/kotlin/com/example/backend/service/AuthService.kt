package com.example.backend.service

import com.example.backend.dto.AuthResponse
import com.example.backend.dto.LoginRequest
import com.example.backend.dto.RegisterRequest
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userDetailsManager: UserDetailsManager,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager
) {

    fun register(data: RegisterRequest,req: HttpServletRequest): AuthResponse {
        // Check if user exists
        if (userDetailsManager.userExists(data.username)) {
            throw RuntimeException("Username already exists")
        }

        // Create user with USER role
        val user: UserDetails = User.builder()
            .username(data.username)
            .password(passwordEncoder.encode(data.password))
            .roles("USER")
            .build()

        userDetailsManager.createUser(user)

        return AuthResponse(
            id = 0,
            username = user.username
        )
    }

    fun login(data: LoginRequest,req: HttpServletRequest): AuthResponse {
        print("Logging in as ${data.username}")
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                data.username,
                data.password
            )
        )

        // Create session and cookie
        val context = SecurityContextHolder.createEmptyContext()
        context.authentication = authentication
        SecurityContextHolder.setContext(context)

        req.session.setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            context
        )

        return if (authentication.isAuthenticated) {
            AuthResponse(
                id = 0,
                username = data.username,
            )
        } else {
            throw RuntimeException("Invalid credentials")
        }
    }
}