package com.github.diogodelima.groupsservice.repositories

import com.github.diogodelima.groupsservice.domain.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<Group, Int>