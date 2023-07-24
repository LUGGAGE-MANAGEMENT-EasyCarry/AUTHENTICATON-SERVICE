package com.example.luggagesystemauthenticationapi.configuration

import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(private val jwtService:JwtService) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: jakarta.servlet.http.HttpServletRequest,
        response: jakarta.servlet.http.HttpServletResponse,
        filterChain: jakarta.servlet.FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response) //go next filter
            return //we dont wanna continue execution rest
        }
        val jwt=authHeader.substring(7)
        val userEmail=jwtService.extractUserName(jwt)   // TODO extract userEmail from jwt token

    }
}