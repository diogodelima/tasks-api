package com.github.diogodelima.groupsservice.repositories

import com.github.diogodelima.groupsservice.domain.Group
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<Group, Int> {

    @Query("SELECT g FROM Group g JOIN g.members gp WHERE gp.id.userId = :userId")
    fun findGroupsByUserId(@Param("userId") userId: Int, pageable: Pageable): Page<Group>

}