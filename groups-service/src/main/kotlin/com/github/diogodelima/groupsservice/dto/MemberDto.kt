package com.github.diogodelima.groupsservice.dto

import com.github.diogodelima.groupsservice.domain.Group
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemberDto(

    @SerialName("member_id")
    val memberId: Int,

    val role: Group.Role

)