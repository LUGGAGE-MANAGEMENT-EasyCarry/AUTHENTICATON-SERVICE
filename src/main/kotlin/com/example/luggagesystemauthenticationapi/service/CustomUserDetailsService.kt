package com.example.luggagesystemauthenticationapi.service

import com.example.luggagesystemauthenticationapi.domain.model.Users
import com.example.luggagesystemauthenticationapi.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val userInfo = userRepository.findByEmail(email)
        return Users(userInfo.id, userInfo.username, userInfo.email, userInfo.password)
    }
}
