package com.maeharin.factlin_sample.infrastructure.repository

import com.maeharin.factlin_sample.domain.User
import com.maeharin.factlin_sample.domain.UserJobType
import com.maeharin.factlin_sample.domain.UserStatus
import com.maeharin.factlin_sample.extension.toJavaLocalDateTime
import com.maeharin.factlin_sample.extension.toJavaLodalDate
import com.maeharin.factlin_sample.extension.toJodaDateTime
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime

object UserTable: IntIdTable("users") {
    val name = varchar("name", 256)
    val job = varchar("job", 256)
    val status = varchar("status", 256)
    val age = integer("age")
    val score = decimal("score", 12, 2)
    val is_admin = bool("is_admin")
    val birth_day = date("birth_day")
    val nick_name = varchar("nick_name", 256).nullable()
    val created_timestamp = datetime("created_timestamp")
    val updated_timestamp = datetime("updated_timestamp").nullable()
}

class UserRepository {
    fun create(user: User): Int {
        val userId = UserTable.insert {
            it[name] = user.name
            it[job] = user.job.name
            it[status] = user.status.name
            it[age] = user.age
            it[score] = user.score
            it[is_admin] = user.isAdmin
            it[birth_day] = user.birthDay.toJodaDateTime()
            it[nick_name] = user.nickName
            it[created_timestamp] = DateTime.now()
            it[updated_timestamp] = null
        } get UserTable.id

        return userId?.value ?: throw Exception("create user fail")
    }

    fun update(id: Int, user: User) {
        UserTable.update({ UserTable.id eq id }) {
            it[name] = user.name
            it[job] = user.job.name
            it[status] = user.status.name
            it[age] = user.age
            it[score] = user.score
            it[is_admin] = user.isAdmin
            it[birth_day] = user.birthDay.toJodaDateTime()
            it[nick_name] = user.nickName
            it[created_timestamp] = user.createdTimestamp!!.toJodaDateTime()
            it[updated_timestamp] = DateTime.now()
        }
    }

    fun delete(id: Int) {
        UserTable.deleteWhere { UserTable.id eq id }
    }

    fun findAll(): List<User> {
        return UserTable.selectAll().map { mapToUser(it) }
    }

    fun findById(id: Int): User {
        //return UserTable.select { UserTable.id eq id }.first()?.let { mapToUser(it) } ?: throw Exception("user id: ${id} is not found")
        return UserTable.select { UserTable.id eq id }.first().let { mapToUser(it) }
    }

    private fun mapToUser(row: ResultRow): User {
        return User(
                id = row[UserTable.id].value,
                name = row[UserTable.name],
                job = UserJobType.valueOf(row[UserTable.job]),
                status = UserStatus.valueOf(row[UserTable.status]),
                age = row[UserTable.age],
                score = row[UserTable.score],
                isAdmin = row[UserTable.is_admin],
                birthDay = row[UserTable.birth_day].toJavaLodalDate(),
                nickName = row[UserTable.nick_name],
                createdTimestamp = row[UserTable.created_timestamp].toJavaLocalDateTime(),
                updatedTimestamp = row[UserTable.updated_timestamp]?.toJavaLocalDateTime()
        )
    }
}
