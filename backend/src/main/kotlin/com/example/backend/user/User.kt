package com.example.backend.user

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class User(
    val username: String,
    @Column("password_hash")
    val passwordHash: String,
    @Id val id: Long? = null
)