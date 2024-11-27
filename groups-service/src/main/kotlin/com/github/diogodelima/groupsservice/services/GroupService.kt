package com.github.diogodelima.groupsservice.services

import com.github.diogodelima.groupsservice.domain.*
import com.github.diogodelima.groupsservice.repositories.GroupRepository
import com.github.diogodelima.groupsservice.repositories.GroupMemberRepository
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GroupService(

    private val groupRepository: GroupRepository,
    private val groupMemberRepository: GroupMemberRepository

) {

    private val restTemplate = RestTemplate()

    fun create(name: String, description: String?, token: String): Group {

        val group = groupRepository.save(
            Group(
                name = name,
                description = description
            )
        )

        val userId = getUserId(token)

        val member = groupMemberRepository.save(
            GroupMember(
                id = GroupMemberId(userId, group.id),
                group = group,
                role = Group.Role.OWNER
            )
        )

        return group.copy(members = listOf(member))
    }

    private fun getUserId(token: String): Int {

        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer $token")
        val httpEntity = HttpEntity(null, headers)
        val response = restTemplate.exchange("http://authorization-server:8080/userinfo", HttpMethod.GET, httpEntity, Map::class.java)
        val body = response.body as Map<*, *>

        return body["user_id"] as Int
    }

}