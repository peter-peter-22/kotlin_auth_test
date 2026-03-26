package com.example.backend.user

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("USERS")
data class User(
    val username: String,
    @Column("PASSWORD_HASH")
    val passwordHash: String,
    @Id val id: Long? = null
)