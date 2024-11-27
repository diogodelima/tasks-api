package com.github.diogodelima.groupsservice.services

import com.github.diogodelima.groupsservice.domain.Group
import com.github.diogodelima.groupsservice.domain.GroupMember
import com.github.diogodelima.groupsservice.domain.GroupMemberId
import com.github.diogodelima.groupsservice.exceptions.GroupAccessDeniedException
import com.github.diogodelima.groupsservice.exceptions.GroupNotFoundException
import com.github.diogodelima.groupsservice.repositories.GroupMemberRepository
import com.github.diogodelima.groupsservice.repositories.GroupRepository
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

    fun getGroupById(groupId: Int, userId: Int): Group {

        val group = groupRepository.findById(groupId).orElseThrow { GroupNotFoundException() }

        if (group.members.none { it.id.userId == userId })
            throw GroupAccessDeniedException()

        return group
    }

}