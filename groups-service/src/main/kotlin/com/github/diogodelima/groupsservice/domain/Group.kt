package com.github.diogodelima.groupsservice.domain

import jakarta.persistence.*

@Entity
@Table(name = "groups")
data class Group(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false)
    val name: String,

    val description: String? = null,

    @OneToMany(mappedBy = "group", cascade = [CascadeType.ALL], orphanRemoval = true)
    val members: List<GroupMember> = emptyList(),

    @ElementCollection
    val tasksIds: List<Int> = emptyList(),

) {

    fun getMember(userId: Int) = members.find { it.id.userId == userId }

    enum class Role {

        MEMBER,
        MODERATOR,
        OWNER

    }

}