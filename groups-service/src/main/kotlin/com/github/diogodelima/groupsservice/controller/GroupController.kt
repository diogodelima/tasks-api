package com.github.diogodelima.groupsservice.controller

import com.github.diogodelima.groupsservice.domain.Group
import com.github.diogodelima.groupsservice.dto.*
import com.github.diogodelima.groupsservice.services.GroupService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/groups")
class GroupController(

    private val groupService: GroupService

) {

    @PostMapping
    fun create(
        @RequestHeader("userId") userId: String,
        @RequestBody @Valid dto: GroupCreateDto
    ): ResponseEntity<ApiResponseDto<GroupDto>> {

        val group = groupService.create(dto.name, dto.description, userId.toInt())

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponseDto(
                    message = "Group created successfully",
                    data = group.toDto()
                )
            )

    }

    @PutMapping("/{groupId}")
    fun editGroupById(
        @RequestHeader("userId") userId: String,
        @PathVariable groupId: Int,
        @RequestBody @Valid dto: GroupEditDto
    ): ResponseEntity<ApiResponseDto<GroupDto>> {

        val group = groupService.editGroupById(groupId, userId.toInt(), dto.name, dto.description)

        return ResponseEntity
            .ok(
                ApiResponseDto(
                    message = "Group edited successfully",
                    data = group.toDto()
                )
            )
    }

    @GetMapping("/{groupId}")
    fun getGroupById(
        @RequestHeader("userId") userId: String,
        @PathVariable groupId: Int
    ): ResponseEntity<ApiResponseDto<GroupDto>> {

        val group = groupService.getGroupById(groupId, userId.toInt())

        return ResponseEntity
            .ok(
                ApiResponseDto(
                    message = "Group found successfully",
                    data = group.toDto()
                )
            )
    }

    @GetMapping("/page/{pageNumber}")
    fun getGroups(
        @RequestHeader("userId") userId: String,
        @PathVariable pageNumber: Int
    ): ResponseEntity<ApiResponseDto<PageDto<GroupDto>>> {

        val page = groupService.getGroups(userId.toInt(), pageNumber - 1)

        return ResponseEntity
            .ok(
                ApiResponseDto(
                    message = "Groups retrieved successfully",
                    data = PageDto(
                        content = page.map { it.toDto() }.toList(),
                        currentPage = pageNumber,
                        pageSize = page.size,
                        totalPages = page.totalPages,
                        totalElements = page.totalElements
                    )
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