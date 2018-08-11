package com.maeharin.factlin_sample.domain

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

class User(
        val id: Int?,
        val name: String,
        val job: UserJobType,
        val status: UserStatus,
        val age: Int,
        val score: BigDecimal,
        val isAdmin: Boolean,
        val birthDay: LocalDate,
        val nickName: String?,
        val createdTimestamp: LocalDateTime? = null,
        val updatedTimestamp: LocalDateTime? = null
)

enum class UserJobType {
    ENGINEER,
    TEACHER
}

enum class UserStatus {
    ACTIVE,
    STOPPED,
}
