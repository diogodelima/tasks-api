package com.github.diogodelima.groupsservice.exceptions

class GroupAccessDeniedException(

    override val message: String? = "You are not member of this group"

) : RuntimeException(message)