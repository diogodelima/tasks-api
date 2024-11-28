package com.github.diogodelima.groupsservice.exceptions

import com.github.diogodelima.groupsservice.domain.Group

class GroupPermissionException(

    role: Group.Role,
    override val message: String? = "You need to be at least ${role.name} to perform this action"

) : RuntimeException(message)