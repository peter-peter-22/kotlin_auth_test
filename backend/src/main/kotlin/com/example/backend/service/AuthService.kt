package com.example.backend.service

import com.example.backend.dto.AuthResponse
import com.example.backend.dto.LoginRequest
import com.example.backend.dto.RegisterRequest
import com.example.backend.repository.PlaceholderUserRepository
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val repo: PlaceholderUserRepository
) {

    fun register(data: RegisterRequest,req: HttpServletRequest): AuthResponse {
        // Check if a user exists
        if (repo.findByUsername(data.username) != null) {
            throw RuntimeException("Username already exists")
        }

        // Create user with a USER role
        val user = com.example.backend.entity.User(data.username, passwordEncoder.encode(data.password))

        repo.save(user)

        return AuthResponse(
            id = 0,
            username = user.username
        )
    }

    fun login(data: LoginRequest,req: HttpServletRequest): AuthResponse {
        println("Logging in as ${data.username}")
        val authentication: Authentication = authenticate(data.username, data.password)

        // Create a session and cookie
        val context = SecurityContextHolder.createEmptyContext()
        context.authentication = authentication
        SecurityContextHolder.setContext(context)

        req.session.setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            context
        )

        return AuthResponse(
            id = 0,
            username = data.username,
        )
    }

    private fun authenticate(username: String, password: String?): Authentication {
        val userObject = repo.findByUsername(username)
        if (userObject == null) {
            println("User not found")
            println(repo.users)
            throw BadCredentialsException("Invalid username and password")
        }

        val userDetails: UserDetails? = User
            .withUsername(userObject.username)
            .password(userObject.passwordHash)
            .roles("USER")
            .build()

        println("Sig in in user details $userDetails")

        if (userDetails == null) {
            println("Sign in details null $userDetails")

            throw BadCredentialsException("Invalid username and password")
        }
        if (!passwordEncoder.matches(password, userDetails.password)) {
            println("Sign in userDetails password mismatch $userDetails")

            throw BadCredentialsException("Invalid password")
        }
        return UsernamePasswordAuthenticationToken(userDetails, password, userDetails.authorities)
    }
}