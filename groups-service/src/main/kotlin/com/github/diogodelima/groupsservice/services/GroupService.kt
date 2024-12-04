package com.github.diogodelima.groupsservice.services

import com.github.diogodelima.groupsservice.domain.Group
import com.github.diogodelima.groupsservice.domain.GroupMember
import com.github.diogodelima.groupsservice.domain.GroupMemberId
import com.github.diogodelima.groupsservice.exceptions.GroupAccessDeniedException
import com.github.diogodelima.groupsservice.exceptions.GroupNotFoundException
import com.github.diogodelima.groupsservice.exceptions.GroupPermissionException
import com.github.diogodelima.groupsservice.repositories.GroupMemberRepository
import com.github.diogodelima.groupsservice.repositories.GroupRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

const val PAGE_SIZE = 10

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

    fun getGroups(userId: Int, pageNumber: Int): Page<Group> =
        groupRepository.findGroupsByUserId(userId, PageRequest.of(pageNumber, PAGE_SIZE))

    fun getGroupById(groupId: Int, userId: Int): Group {

        val group = groupRepository.findById(groupId).orElseThrow { GroupNotFoundException() }

        if (group.members.none { it.id.userId == userId })
            throw GroupAccessDeniedException()

        return group
    }

    fun editGroupById(groupId: Int, userId: Int, name: String?, description: String?): Group {

        val group = groupRepository.findById(groupId).orElseThrow { GroupNotFoundException() }
        val member = group.getMember(userId) ?: throw GroupAccessDeniedException()
        val role = Group.Role.MODERATOR

        if (!member.isAtLeast(role))
            throw GroupPermissionException(role)

        return groupRepository.save(
            group.copy(
                name = name ?: group.name,
                description = description ?: group.description
            ))

    }

    fun getTasks(groupId: Int, userId: Int): List<Int> {

        val group = groupRepository.findById(groupId).orElseThrow { GroupNotFoundException() }

        if (group.members.none { it.id.userId == userId })
            throw GroupAccessDeniedException()

        return group.tasksIds
    }

    fun deleteGroup(groupId: Int, userId: Int) {

        val group = groupRepository.findById(groupId).orElseThrow { GroupNotFoundException() }
        val member = group.getMember(userId) ?: throw GroupAccessDeniedException()
        val role = Group.Role.OWNER

        if (!member.isAtLeast(role))
            throw GroupPermissionException(role)

        groupRepository.delete(group)
    }

}