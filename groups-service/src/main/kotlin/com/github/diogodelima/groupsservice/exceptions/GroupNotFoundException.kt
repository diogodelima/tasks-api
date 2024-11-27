package com.github.diogodelima.groupsservice.exceptions

class GroupNotFoundException(

    override val message: String? = "Group not found"

) : RuntimeException(message)