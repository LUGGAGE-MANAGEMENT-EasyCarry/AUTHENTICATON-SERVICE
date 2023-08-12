package com.example.luggagesystemauthenticationapi.domain.model


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.userdetails.UserDetails


@Table(name="users")
@Entity
open class Users (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int,
    @Column(name="username")
    private var username:String,
    var email:String,
    private var password:String,
    //private val authorities: MutableCollection<GrantedAuthority>

):UserDetails{

    override fun getAuthorities() = null
    override fun getPassword() = password
    override fun getUsername() = email
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired()= true
    override fun isEnabled() = true
    fun setPassword(password: String){
        this.password=password
    }
}