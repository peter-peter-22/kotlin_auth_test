package com.example.backend.repository

import com.example.backend.entity.User
import org.springframework.stereotype.Repository

@Repository
class PlaceholderUserRepository {
    val users = mutableListOf<User>()

    fun save(user: User) {
        users.add(user)
    }
    fun findByUsername(username: String): User? {
        return users.find { it.username == username }
    }
}