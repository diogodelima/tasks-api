package com.github.diogodelima.groupsservice.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class GroupCreateDto(

    @field:NotNull
    val id: Int,

    @field:NotBlank
    val name: String,

    val description: String? = null,

)