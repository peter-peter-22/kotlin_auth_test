package com.example.backend.controller

import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController {
    @GetMapping("/private")
    fun private(auth: Authentication): String {
        return "Hello ${auth.name}"
    }

    @GetMapping("/optional")
    fun optional(
        @AuthenticationPrincipal user: UserDetails?
    ): String {
        return user?.username ?: "anonymous"
    }

    @GetMapping("/public")
    fun public(): String {
        return "public"
    }
}