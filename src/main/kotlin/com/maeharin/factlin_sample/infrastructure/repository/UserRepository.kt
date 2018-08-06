package com.maeharin.factlin_sample.infrastructure.repository

import com.maeharin.factlin_sample.domain.User
import com.maeharin.factlin_sample.domain.UserJobType
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

object Users: IntIdTable() {
    val name = varchar("name", 256)
    val job = varchar("job", 256)
}

class UserRepository {
    fun create(user: User): Int {
        val userId = Users.insert {
            it[name] = user.name
            it[job] = user.job.name
        } get Users.id

        return userId?.value ?: throw Exception("create user fail")
    }

    fun findAll(): List<User> {
        return Users.selectAll().map {
            User(
                    id = it[Users.id].value,
                    name = it[Users.name],
                    job = UserJobType.valueOf(it[Users.job])
            )
        }
    }
}
