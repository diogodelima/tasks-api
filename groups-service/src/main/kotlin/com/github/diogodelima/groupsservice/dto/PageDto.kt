package com.github.diogodelima.groupsservice.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageDto<T>(

    val content: List<T>,

    @SerialName("current_page")
    val currentPage: Int,

    @SerialName("page_size")
    val pageSize: Int,

    @SerialName("total_elements")
    val totalElements: Long,

    @SerialName("total_pages")
    val totalPages: Int

)