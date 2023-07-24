package com.example.luggagesystemauthenticationapi.repository

import com.example.luggagesystemauthenticationapi.user.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface UserRepository : CoroutineCrudRepository<User, UUID> {
    suspend fun findByEmail(email: String): User

}