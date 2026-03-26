package com.example.backend.user

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
@Suppress("unused")
@ConditionalOnProperty(name = ["app.my-config.user-repository"], havingValue = "database")
interface JDBCUserRepository: CrudRepository<User, Long>, UserRepository {
    override fun findByUsername(username: String): User?
}