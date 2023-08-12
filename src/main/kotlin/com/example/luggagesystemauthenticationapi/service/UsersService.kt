package com.example.luggagesystemauthenticationapi.service

import com.example.luggagesystemauthenticationapi.domain.dto.AuthRequest
import com.example.luggagesystemauthenticationapi.domain.model.Users
import com.example.luggagesystemauthenticationapi.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class UsersService(private val userRepository: UserRepository) {

    fun findByLoginDto(authRequest: AuthRequest):Users{
        userRepository.findByEmail(authRequest.email)?.let { user ->
            if (BCryptPasswordEncoder().matches(authRequest.password,user.password)) return user
        }
        throw RuntimeException("User Not Found")
    }
}