package com.example.backend.service

import com.example.backend.repository.PlaceholderUserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImplementation(val repo: PlaceholderUserRepository): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails? {
        if (username == null) throw RuntimeException("Username cannot be null")
        val user = repo.findByUsername(username) ?: throw RuntimeException("User not found")
        return User.withUsername(user.username).password(user.passwordHash).roles("USER").build()
    }
}