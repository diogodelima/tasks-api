package com.github.diogodelima.groupsservice.services

import org.mockito.BDDMockito.given

import com.github.diogodelima.groupsservice.domain.Group
import com.github.diogodelima.groupsservice.domain.GroupMember
import com.github.diogodelima.groupsservice.domain.GroupMemberId
import com.github.diogodelima.groupsservice.exceptions.GroupAccessDeniedException
import com.github.diogodelima.groupsservice.exceptions.GroupNotFoundException
import com.github.diogodelima.groupsservice.repositories.GroupMemberRepository
import com.github.diogodelima.groupsservice.repositories.GroupRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class GroupServiceTest {

    @Mock
    private lateinit var groupRepository: GroupRepository

    @Mock
    private lateinit var groupMemberRepository: GroupMemberRepository

    @InjectMocks
    private lateinit var groupService: GroupService

    @Test
    fun `create a valid group should succeeds`() {

        val expected = group

        given(groupRepository.save(Mockito.any(Group::class.java))).willReturn(group)
        given(groupMemberRepository.save(Mockito.any(GroupMember::class.java))).willReturn(expected.members[0])

        val actual = groupService.create("Tarefas de Casa", "Tarefas de casa para a primeira semana de dezembro", 1)

        assertEquals(expected, actual)
    }

    @Test
    fun `create a valid group without description should succeeds`() {

        val expected = group

        given(groupRepository.save(Mockito.any(Group::class.java))).willReturn(group)
        given(groupMemberRepository.save(Mockito.any(GroupMember::class.java))).willReturn(expected.members[0])

        val actual = groupService.create("Tarefas de Casa", "Tarefas de casa para a primeira semana de dezembro", 1)

        assertEquals(expected, actual)
    }

    @Test
    fun `try retrieve a group that the user is not a member of should throw an exception`() {

        val expected = group

        given(groupRepository.findById(Mockito.any(Int::class.java))).willReturn(Optional.of(expected))

        assertThrows<GroupAccessDeniedException> {
            groupService.getGroupById(1, 2)
        }

    }

    @Test
    fun `try retrieve a group that doesn't exists should throw an exception`() {

        given(groupRepository.findById(Mockito.any(Int::class.java))).willReturn(Optional.empty())

        assertThrows<GroupNotFoundException> {
            groupService.getGroupById(4, 1)
        }

    }

}

private val group = Group(
    id = 1,
    name = "Tarefas de Casa",
    description = "Tarefas de casa para a primeira semana de dezembro",
    members = listOf(
        GroupMember(
            id = GroupMemberId(userId = 1, groupId = 1),
            group = Group(
                id = 1,
                name = "Tarefas de Casa",
                description = "Tarefas de casa para a primeira semana de dezembro"
            ),
            role = Group.Role.OWNER
        )
    )
)