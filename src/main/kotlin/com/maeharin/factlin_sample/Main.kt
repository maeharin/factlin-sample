package com.maeharin.factlin_sample

import com.maeharin.factlin_sample.domain.UserJobType
import com.maeharin.factlin_sample.usecase.*
import com.maeharin.factlin_sample.views.userDetail
import com.maeharin.factlin_sample.views.userForm
import com.maeharin.factlin_sample.views.userIndex
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.request.receiveParameters
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database
import java.time.LocalDate


fun Application.module() {
    val hikariConfig = HikariConfig().also {
        val host = System.getenv("DB_HOST")
        it.jdbcUrl = "jdbc:postgresql://$host/dvdrental"
        it.username = "postgres"
        it.password = ""
        it.driverClassName = "org.postgresql.Driver"
    }
    val dataSource = HikariDataSource(hikariConfig)
    Database.connect(dataSource)

    install(DefaultHeaders)
    install(CallLogging)

    routing {
        get("/") {
            call.respondText("Hello World!", ContentType.Text.Plain)
        }
        get("/users/new") {
            call.respondHtml { userForm(null) }
        }
        post("/users") {
            val params = call.receiveParameters()
            val input = CreateOrUpdateUserInput(
                    name = params["name"]!!,
                    job = UserJobType.valueOf(params["job"]!!),
                    age = params["age"]!!.toInt(),
                    birthDay = LocalDate.parse(params["birthDay"]),
                    nickName = params["nickName"]
            )
            val userId = createUser(input)
            call.respondRedirect("/users")
        }
        get ("/users") {
            val users = showUsers()
            call.respondHtml { userIndex(users) }
        }
        get ("/users/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw RuntimeException() // todo not found
            val user = showUserById(id)
            call.respondHtml { userDetail(user) }
        }
        get ("/users/edit/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw RuntimeException() // todo not found
            val user = showUserById(id)
            call.respondHtml { userForm(user) }
        }
        post("/users/update/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw RuntimeException() // todo not found
            val params = call.receiveParameters()
            val input = CreateOrUpdateUserInput(
                    name = params["name"]!!,
                    job = UserJobType.valueOf(params["job"]!!),
                    age = params["age"]!!.toInt(),
                    birthDay = LocalDate.parse(params["birthDay"]),
                    nickName = params["nickName"]
            )
            updateUser(id, input)
            call.respondRedirect("/users")
        }
        post("/users/delete/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw RuntimeException() // todo not found
            deleteUser(id)
            call.respondRedirect("/users")
        }
        //get("/api/users") {
        //    val users = showUsers()
        //}
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080,
            module = Application::module
    ).start(wait = true)
}
