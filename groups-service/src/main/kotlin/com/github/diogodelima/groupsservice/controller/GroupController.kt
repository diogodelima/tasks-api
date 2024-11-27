package com.github.diogodelima.groupsservice.controller

import com.github.diogodelima.groupsservice.domain.Group
import com.github.diogodelima.groupsservice.dto.ApiResponseDto
import com.github.diogodelima.groupsservice.dto.GroupCreateDto
import com.github.diogodelima.groupsservice.dto.GroupDto
import com.github.diogodelima.groupsservice.dto.MemberDto
import com.github.diogodelima.groupsservice.services.GroupService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/groups")
class GroupController(

    private val groupService: GroupService

) {

    @PostMapping
    fun create(@RequestBody @Valid dto: GroupCreateDto): ResponseEntity<ApiResponseDto<GroupDto>> {

        val group = groupService.create(dto.name, dto.description, dto.id)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponseDto(
                    message = "Group created successfully",
                    data = group.toDto()
                )
            )

    }

}

private fun Group.toDto() = GroupDto(
    id = this.id,
    name = this.name,
    description = this.description,
    tasks = this.tasksIds,
    members = this.members.map { MemberDto(it.id.userId, it.role) }
)