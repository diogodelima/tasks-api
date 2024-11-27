package com.github.diogodelima.tasksservice.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tasks")
data class Task(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false)
    val title: String,

    val description: String? = null,

    @Enumerated(EnumType.STRING)
    val priority: Priority = Priority.LOW,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: Status = Status.PENDING,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val deadline: LocalDateTime,

    val startedAt: LocalDateTime? = null,

    val completedAt: LocalDateTime? = null,

    @Column(nullable = false)
    val groupId: Int,

    @ElementCollection
    val membersIds: List<Int>

) {

    enum class Status {

        PENDING,
        COMPLETED,
        IN_PROGRESS,
        DELAYED

    }

    enum class Priority {

        LOW,
        MEDIUM,
        HIGH,
        URGENT

    }

}