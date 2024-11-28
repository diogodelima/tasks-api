package com.github.diogodelima.groupsservice.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseDto<T>(

    val message: String? = null,
    val data: T? = null

)