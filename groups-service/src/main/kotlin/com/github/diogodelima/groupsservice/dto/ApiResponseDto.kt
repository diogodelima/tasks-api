package com.github.diogodelima.groupsservice.dto

data class ApiResponseDto<T>(

    val message: String? = null,
    val data: T? = null

)