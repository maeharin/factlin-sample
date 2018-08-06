package com.maeharin.factlin_sample.domain

class User(
        val id: Int?,
        val name: String,
        val job: UserJobType
)

enum class UserJobType {
    ENGINEER,
    TEACHER
}

