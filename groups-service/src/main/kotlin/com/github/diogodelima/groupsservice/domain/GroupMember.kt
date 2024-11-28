package com.github.diogodelima.groupsservice.domain

import jakarta.persistence.*


@Embeddable
data class GroupMemberId(
    val userId: Int,
    val groupId: Int
)

@Entity
@Table(name = "group_members")
data class GroupMember(

    @EmbeddedId
    val id: GroupMemberId,

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    val group: Group,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Group.Role

) {

    fun isAtLeast(role: Group.Role) = this.role.ordinal >= role.ordinal

}
