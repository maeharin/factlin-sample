package com.maeharin.factlin_sample.usecase

import com.maeharin.factlin_sample.domain.UserJobType
import com.maeharin.factlin_sample.domain.User
import com.maeharin.factlin_sample.domain.UserStatus
import com.maeharin.factlin_sample.infrastructure.repository.UserRepository
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

data class CreateOrUpdateUserInput(
        val name: String,
        val job: UserJobType,
        val age: Int,
        val birthDay: LocalDate,
        val nickName: String?
)

fun createUser(input: CreateOrUpdateUserInput): Int {
    return transaction {
        val user = User(
                id = null,
                name = input.name,
                job = input.job,
                status = UserStatus.ACTIVE,
                age = input.age,
                score = 0.0.toBigDecimal(),
                isAdmin = false,
                birthDay = input.birthDay,
                nickName = input.nickName
        )

        val userId = UserRepository().create(user)
        userId
    }
}

fun updateUser(id: Int, input: CreateOrUpdateUserInput) {
    return transaction {
        val oldUser = UserRepository().findById(id)

        val newUser = User(
                id = id,
                name = input.name,
                job = input.job,
                status = oldUser.status,
                age = input.age,
                score = oldUser.score,
                isAdmin = oldUser.isAdmin,
                birthDay = input.birthDay,
                nickName = input.nickName,
                createdTimestamp = oldUser.createdTimestamp
        )

        UserRepository().update(id, newUser)
    }
}

fun deleteUser(id: Int) {
    return transaction {
        UserRepository().delete(id)
    }
}

fun showUsers(): List<User> {
    return transaction {
        UserRepository().findAll()
    }
}

fun showUserById(id: Int): User {
    return transaction {
        UserRepository().findById(id)
    }
}
