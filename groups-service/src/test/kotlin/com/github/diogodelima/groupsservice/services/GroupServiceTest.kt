package com.github.diogodelima.groupsservice.services

import org.mockito.BDDMockito.given

import com.github.diogodelima.groupsservice.domain.Group
import com.github.diogodelima.groupsservice.domain.GroupMember
import com.github.diogodelima.groupsservice.domain.GroupMemberId
import com.github.diogodelima.groupsservice.exceptions.GroupAccessDeniedException
import com.github.diogodelima.groupsservice.exceptions.GroupNotFoundException
import com.github.diogodelima.groupsservice.exceptions.GroupPermissionException
import com.github.diogodelima.groupsservice.repositories.GroupMemberRepository
import com.github.diogodelima.groupsservice.repositories.GroupRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.argThat
import org.mockito.ArgumentMatchers.eq
import org.mockito.BDDMockito.any
import org.mockito.InjectMocks
import org.mockito.Mock
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

        given(groupRepository.save(any(Group::class.java))).willReturn(group)
        given(groupMemberRepository.save(any(GroupMember::class.java))).willReturn(expected.members[0])

        val actual = groupService.create("Tarefas de Casa", "Tarefas de casa para a primeira semana de dezembro", 1)

        assertEquals(expected, actual)
    }

    @Test
    fun `create a valid group without description should succeeds`() {

        val expected = group

        given(groupRepository.save(any(Group::class.java))).willReturn(group)
        given(groupMemberRepository.save(any(GroupMember::class.java))).willReturn(expected.members[0])

        val actual = groupService.create("Tarefas de Casa", null, 1)

        assertEquals(expected, actual)
    }

    @Test
    fun `try retrieve a group that the user is not a member of should throw an exception`() {

        val expected = group

        given(groupRepository.findById(eq(1))).willReturn(Optional.of(expected))

        assertThrows<GroupAccessDeniedException> {
            groupService.getGroupById(1, 2)
        }

    }

    @Test
    fun `try retrieve a group that doesn't exists should throw an exception`() {

        given(groupRepository.findById(argThat { it != 1 })).willReturn(Optional.empty())

        assertThrows<GroupNotFoundException> {
            groupService.getGroupById(4, 1)
        }

    }

    @Test
    fun `edit a group that the user is not a member of should throw an exception`() {

        val expected = group

        given(groupRepository.findById(eq(1))).willReturn(Optional.of(expected))

        assertThrows<GroupAccessDeniedException> {
            groupService.editGroupById(1, 3, "Tarefas de Casa", "Tarefas de casa para a primeira semana de dezembro")
        }

    }

    @Test
    fun `edit a group that doesn't exists should throw an exception`() {

        given(groupRepository.findById(argThat { it != 1 })).willReturn(Optional.empty())

        assertThrows<GroupNotFoundException> {
            groupService.editGroupById(4, 1, "Tarefas de Casa", "Tarefas de casa para a primeira semana de dezembro")
        }

    }

    @Test
    fun `edit a group that the user is not at least moderator should thrown an exception`() {

        given(groupRepository.findById(eq(4))).willReturn(Optional.of(groupWithOtherMembers))

        assertThrows<GroupPermissionException> {
            groupService.editGroupById(4, 2, "Tarefas de Casa", "Tarefas de casa para a primeira semana de dezembro")
        }

    }

    @Test
    fun `edit the description of a group should succeed`() {

        given(groupRepository.findById(eq(2))).willReturn(Optional.of(groupBeforeEdit))
        given(groupRepository.save(eq(groupAfterEdit))).willReturn(groupAfterEdit)

        val group = groupService.editGroupById(2, 1, null, "Tarefas da Escola para a primeira semana de fevereiro")

        assertEquals(groupAfterEdit, group)
    }

    @Test
    fun `get tasks from a group that doesn't exists should throw an exception`() {

        given(groupRepository.findById(argThat { it != 1 })).willReturn(Optional.empty())

        assertThrows<GroupNotFoundException> {
            groupService.getTasks(4, 1)
        }

    }

    @Test
    fun `get tasks from a group that the user is not a member of should throw an exception`() {

        val expected = group

        given(groupRepository.findById(eq(1))).willReturn(Optional.of(expected))

        assertThrows<GroupAccessDeniedException> {
            groupService.getTasks(1, 2)
        }

    }

    @Test
    fun `get tasks from a group should succeed`() {

        val expected = listOf(1, 2, 3)

        given(groupRepository.findById(eq(1))).willReturn(Optional.of(groupWithTasks))

        val actual = groupService.getTasks(1, 1)
        assertEquals(expected, actual)
    }

}

private val groupWithTasks = Group(
    id = 3,
    name = "Tarefas do trabalho",
    description = "Tarefas relacionadas ao trabalho para o mês de dezembro",
    members = listOf(
        GroupMember(
            id = GroupMemberId(userId = 1, groupId = 3),
            group = Group(
                id = 3,
                name = "Tarefas do trabalho",
                description = "Tarefas relacionadas ao trabalho para o mês de dezembro",
            ),
            role = Group.Role.OWNER
        )
    ),
    tasksIds = listOf(1, 2, 3)
)

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

private val groupWithOtherMembers = Group(
    id = 4,
    name = "Tarefas de Casa",
    description = "Tarefas de casa para a primeira semana de dezembro",
    members = listOf(
        GroupMember(
            id = GroupMemberId(userId = 1, groupId = 4),
            group = Group(
                id = 4,
                name = "Tarefas de Casa",
                description = "Tarefas de casa para a primeira semana de dezembro"
            ),
            role = Group.Role.OWNER
        ),
        GroupMember(
            id = GroupMemberId(userId = 2, groupId = 4),
            group = Group(
                id = 4,
                name = "Tarefas do trabalho",
                description = "Tarefas relacionadas ao trabalho para o mês de dezembro",
            ),
            role = Group.Role.MEMBER
        )
    )
)

private val groupBeforeEdit = Group(
    id = 2,
    name = "Tarefas da Escola",
    description = "Tarefas relacionadas à escola",
    members = listOf(
        GroupMember(
            id = GroupMemberId(userId = 1, groupId = 2),
            group = Group(
                name = "Tarefas da Escola",
                description = "Tarefas relacionadas à escola"
            ),
            role = Group.Role.OWNER
        )
    )
)

private val groupAfterEdit = Group(
    id = 2,
    name = "Tarefas da Escola",
    description = "Tarefas da Escola para a primeira semana de fevereiro",
    members = listOf(
        GroupMember(
            id = GroupMemberId(userId = 1, groupId = 2),
            group = Group(
                name = "Tarefas da Escola",
                description = "Tarefas relacionadas à escola"
            ),
            role = Group.Role.OWNER
        )
    )
)