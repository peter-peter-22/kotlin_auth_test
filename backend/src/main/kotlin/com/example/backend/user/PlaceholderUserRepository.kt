package com.example.backend.user

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository

@Repository
@ConditionalOnProperty(name = ["app.my-config.user-repository"], havingValue = "placeholder", matchIfMissing = true)
class PlaceholderUserRepository: UserRepository {
    val users = mutableListOf<User>()

    override fun save(user: User): User? {
        users.add(user)
        return user
    }

    override fun findById(id: Long): User? {
        return users.find { it.id == id }
    }

    override fun findByUsername(username: String): User? {
        return users.find { it.username == username }
    }
}