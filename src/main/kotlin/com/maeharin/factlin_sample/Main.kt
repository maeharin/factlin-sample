package com.maeharin.factlin_sample

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


fun main(args: Array<String>) {
    val hikariConfig = HikariConfig().also {
        it.jdbcUrl = "jdbc:postgresql://192.168.99.100/dvdrental"
        it.username = "postgres"
        it.password = ""
        it.driverClassName = "org.postgresql.Driver"
    }
    val dataSource = HikariDataSource(hikariConfig)
    Database.connect(dataSource)

    val server = embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                call.respondText("Hello World!", ContentType.Text.Plain)
            }
            get("/createuser") {
                val userId = transaction {
                    addLogger(StdOutSqlLogger)

                    Users.insert {
                        it[name] = "maeharin!"
                    } get Users.id
                }

                call.respondText(userId.toString(), ContentType.Text.Plain)
            }
            // うまく動かない。。。
            //get ("/users") {
            //    val userNames = Users.selectAll().map { it[Users.name] }.joinToString(",")
            //    call.respondText(userNames, ContentType.Text.Plain)
            //}
        }
    }
    server.start(wait = true)
}

object Users: IntIdTable() {
    val name = varchar("name", 256)
}

