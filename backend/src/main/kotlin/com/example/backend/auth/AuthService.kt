package com.example.backend.auth

import com.example.backend.dto.AuthResponse
import com.example.backend.dto.LoginRequest
import com.example.backend.dto.RegisterRequest
import com.example.backend.user.User
import com.example.backend.user.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val passwordEncoder: PasswordEncoder,
    private val repo: UserRepository,
    private val securityContextRepository: SecurityContextRepository
) {
    fun register(data: RegisterRequest): AuthResponse {
        if (repo.findByUsername(data.username) != null) {
            throw RuntimeException("Username already exists")
        }

        val user = User(data.username, passwordEncoder.encode(data.password))
        repo.save(user)

        return AuthResponse(
            id = 0,
            username = user.username
        )
    }

    fun login(data: LoginRequest, req: HttpServletRequest, res: HttpServletResponse): AuthResponse {
        println("Logging in as ${data.username}")
        val authentication: Authentication = authenticate(data.username, data.password)

        val context = SecurityContextHolder.createEmptyContext()
        context.authentication = authentication
        SecurityContextHolder.setContext(context)

        securityContextRepository.saveContext(context, req, res)

        return AuthResponse(
            id = 0,
            username = data.username,
        )
    }

    private fun authenticate(username: String, password: String?): Authentication {
        val userObject = repo.findByUsername(username)
            ?: throw BadCredentialsException("Invalid username and password")

        val userDetails: UserDetails = org.springframework.security.core.userdetails.User
            .withUsername(userObject.username)
            .password(userObject.passwordHash)
            .roles("USER")
            .build()

        if (!passwordEncoder.matches(password, userDetails.password)) {
            throw BadCredentialsException("Invalid password")
        }

        return UsernamePasswordAuthenticationToken(userDetails, password, userDetails.authorities)
    }
}