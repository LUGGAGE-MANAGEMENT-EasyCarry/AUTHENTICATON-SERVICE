package com.example.luggagesystemauthenticationapi.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.time.LocalDate
import java.util.Date

@Component
class JwtService {
    fun generateToken(username: String): String {
        val claims = mutableMapOf<String, Object>()
        return createToken(claims, username)
    }

    fun createToken(claims: Map<String, Object>, username: String): String {
        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 60 * 1000 * 30*40)).signWith(getSignKey(), SignatureAlgorithm.HS256).compact()
    }

    fun extractUserName(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    fun <T> extractClaim(token: String, claimResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimResolver(claims)
    }

    fun extractAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(getSignKey()).parseClaimsJws(token).body
    }

    fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUserName(token)
        return username.equals(userDetails.username) && !isTokenExpired(token)
    }

    fun validateTokenWithParam(token: String){
        Jwts.parser().setSigningKey(getSignKey())
            .parseClaimsJws(token)
    }

    companion object {
        const val SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437"
    }

    fun getSignKey(): Key {
        val key = Decoders.BASE64.decode(SECRET)
        return Keys.hmacShaKeyFor(key)
    }
}