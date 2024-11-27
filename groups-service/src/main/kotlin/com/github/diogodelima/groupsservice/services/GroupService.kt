package com.github.diogodelima.groupsservice.services

import com.github.diogodelima.groupsservice.domain.*
import com.github.diogodelima.groupsservice.repositories.GroupRepository
import com.github.diogodelima.groupsservice.repositories.GroupMemberRepository
import org.springframework.stereotype.Service

@Service
class GroupService(

    private val groupRepository: GroupRepository,
    private val groupMemberRepository: GroupMemberRepository

) {

    fun create(name: String, description: String?, userId: Int): Group {

        val group = groupRepository.save(
            Group(
                name = name,
                description = description
            )
        )

        val member = groupMemberRepository.save(
            GroupMember(
                id = GroupMemberId(userId, group.id),
                group = group,
                role = Group.Role.OWNER
            )
        )

        return group.copy(members = listOf(member))
    }

}