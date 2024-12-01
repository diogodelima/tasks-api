package com.github.diogodelima.groupsservice.exceptions.handler

import com.github.diogodelima.groupsservice.dto.ApiResponseDto
import com.github.diogodelima.groupsservice.exceptions.GroupAccessDeniedException
import com.github.diogodelima.groupsservice.exceptions.GroupNotFoundException
import com.github.diogodelima.groupsservice.exceptions.GroupPermissionException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(GroupNotFoundException::class)
    fun handleNotFound(exception: Exception): ResponseEntity<ApiResponseDto<Any>> =
        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ApiResponseDto(
                    message = exception.message
                )
            )

    @ExceptionHandler(GroupAccessDeniedException::class, GroupPermissionException::class)
    fun handleBadRequest(exception: Exception): ResponseEntity<ApiResponseDto<Any>> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ApiResponseDto(
                    message = exception.message
                )
            )

}