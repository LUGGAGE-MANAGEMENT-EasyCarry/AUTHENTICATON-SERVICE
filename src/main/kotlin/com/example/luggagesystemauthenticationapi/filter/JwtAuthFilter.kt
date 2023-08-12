package com.example.luggagesystemauthenticationapi.filter

import com.example.luggagesystemauthenticationapi.service.CustomUserDetailsService
import com.example.luggagesystemauthenticationapi.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(private val jwtService: JwtService, private val userDetailsService: CustomUserDetailsService) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authHeader = request.getHeader("Authorization")
        var token: String? = null
        var username: String? = null
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7)
            username = jwtService.extractUserName(token)
        }
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(username)
            if (jwtService.validateToken(token!!, userDetails)) {
                val authenticationUsernamePasswordToken = UsernamePasswordAuthenticationToken(userDetails, null, null)
                authenticationUsernamePasswordToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication=authenticationUsernamePasswordToken
            }
        }
        filterChain.doFilter(request,response)
    }

}