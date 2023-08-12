package com.example.luggagesystemauthenticationapi.repository

import com.example.luggagesystemauthenticationapi.domain.model.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<Users, Long> {
     fun findByEmail(email: String): Users

}