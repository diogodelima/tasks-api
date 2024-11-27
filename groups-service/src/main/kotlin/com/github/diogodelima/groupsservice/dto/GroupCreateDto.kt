package com.github.diogodelima.groupsservice.dto

import jakarta.validation.constraints.NotBlank

data class GroupCreateDto(

    @field:NotBlank
    val name: String,

    val description: String? = null,

)