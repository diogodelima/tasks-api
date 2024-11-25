package com.github.diogodelima.authorization.services

import com.github.diogodelima.authorization.domain.User
import com.github.diogodelima.authorization.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(

    private val userRepository: UserRepository

) : UserDetailsService {

    override fun loadUserByUsername(username: String?): User =
        userRepository.findByUsername(username) ?: throw UsernameNotFoundException("Username not found")

}