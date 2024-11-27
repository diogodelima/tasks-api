package com.github.diogodelima.groupsservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GroupsServiceApplication

fun main(args: Array<String>) {
    runApplication<GroupsServiceApplication>(*args)
}
