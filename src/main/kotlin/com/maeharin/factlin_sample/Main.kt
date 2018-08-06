package com.maeharin.factlin_sample

import com.maeharin.factlin_sample.application.usecase.CreateUserInput
import com.maeharin.factlin_sample.application.usecase.createUser
import com.maeharin.factlin_sample.application.usecase.showUsers
import com.maeharin.factlin_sample.domain.UserJobType
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
import kotlinx.html.*
import org.jetbrains.exposed.sql.Database


fun Application.main() {
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

    val server = embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                call.respondText("Hello World!", ContentType.Text.Plain)
            }
            get("/users/new") {
                call.respondHtml {
                    head {
                        title { +"factlin sample web app"}
                    }
                    body {
                        form("/users", encType = FormEncType.applicationXWwwFormUrlEncoded, method = FormMethod.post) {
                            acceptCharset = "utf-8"
                            p {
                                label { +"name: "}
                                textInput { name = "name"}
                            }
                            p {
                                label { +"job: "}
                                textInput { name = "job"}
                            }
                            p {
                                submitInput { value = "送信" }
                            }
                        }
                    }
                }
            }
            post("/users") {
                val params = call.receiveParameters()
                val input = CreateUserInput(
                        name = params["name"]!!,
                        job = UserJobType.valueOf(params["job"]!!)
                )
                val userId = createUser(input)
                call.respondRedirect("/users")
            }
            get ("/users") {
                val users = showUsers()
                call.respondHtml {
                    head {
                        title { +"factlin sample web app"}
                    }
                    body {
                        a("/users/new") { +"新規作成" }
                        ul {
                            users.forEach { user ->
                                li {
                                    +"${user.id} ${user.name} ${user.job}"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    server.start(wait = true)
}

