package com.example.backend.user

interface UserRepository {
    fun findByUsername(username: String): User?
    fun save(user: User): User?
    fun findById(id: Long): User?
}
