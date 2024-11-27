package com.github.diogodelima.tasksservice.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tasks")
class Controller {

    @GetMapping
    fun secure(): ResponseEntity<String> {
        return ResponseEntity.ok("hello world")
    }

}