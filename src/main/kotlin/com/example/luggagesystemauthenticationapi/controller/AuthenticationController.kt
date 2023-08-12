package com.example.luggagesystemauthenticationapi.controller

import com.example.luggagesystemauthenticationapi.domain.dto.AuthRequest
import com.example.luggagesystemauthenticationapi.domain.model.Users
import com.example.luggagesystemauthenticationapi.repository.UserRepository
import com.example.luggagesystemauthenticationapi.service.JwtService
import com.example.luggagesystemauthenticationapi.service.UsersService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val userRepository: UserRepository,
    private val usersService: UsersService,
    private val jwtService: JwtService
) {

    @PostMapping("/authenticate")
    fun authenticateAndGetToken(@RequestBody authRequest: AuthRequest): String {
        val loadedUser = usersService.findByLoginDto(authRequest)
        val token = jwtService.generateToken(loadedUser.username)
        return token
    }

    @PostMapping("/register")
    fun register(@RequestBody users: Users): Users {
        users.password = BCryptPasswordEncoder().encode(users.password)
        return userRepository.save(users)
    }

    @GetMapping("/validate")
    fun validateToken(@RequestParam("token") token: String): String {
        jwtService.validateTokenWithParam(token)
        return "token is valid"
    }

}