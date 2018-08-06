package com.maeharin.factlin_sample.application.usecase

import com.maeharin.factlin_sample.domain.UserJobType
import com.maeharin.factlin_sample.domain.User
import com.maeharin.factlin_sample.infrastructure.repository.UserRepository
import org.jetbrains.exposed.sql.transactions.transaction

data class CreateUserInput(
        val name: String,
        val job: UserJobType
)


fun createUser(input: CreateUserInput): Int {
    val user = User(
            id = null,
            name = input.name,
            job = input.job
    )

    return transaction {
        val userId = UserRepository().create(user)
        userId
    }
}

fun showUsers(): List<User> {
    return transaction {
        UserRepository().findAll()
    }
}
