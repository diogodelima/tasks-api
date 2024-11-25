package com.github.diogodelima.authorization.repositories

import com.github.diogodelima.authorization.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Int> {

    fun findByUsername(username: String?): User?

}