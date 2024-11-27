package com.github.diogodelima.groupsservice.dto

import kotlinx.serialization.Serializable

@Serializable
data class GroupDto(

    val id: Int,

    val name: String,

    val description: String?,

    val members: List<MemberDto>,

    val tasks: List<Int>

)