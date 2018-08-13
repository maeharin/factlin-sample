package factlin_sample.testng

import com.maeharin.factlin.fixtures.UsersFixture
import com.maeharin.factlin.fixtures.insertUsersFixture
import com.maeharin.factlin.fixtures_ext.active
import com.maeharin.factlin.fixtures_ext.stopped
import com.maeharin.factlin_sample.module
import com.ninja_squad.dbsetup.destination.DriverManagerDestination
import com.ninja_squad.dbsetup_kotlin.dbSetup
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.jetbrains.exposed.sql.Database
import org.testng.Assert.assertEquals
import org.testng.annotations.Test

class UserControllerTest {
    val dbUrl = "jdbc:postgresql://${System.getenv("DB_HOST")}/dvdrental"
    val dbUser = "postgres"
    val dbPassword = ""
    val dbDriver = "org.postgresql.Driver"

    val dest = DriverManagerDestination(dbUrl, dbUser, dbPassword)

    init {
        Class.forName(dbDriver)
        Database.connect(url = dbUrl, driver = dbDriver, user = dbUser, password = dbPassword)
    }

    @Test
    fun `hello world`() {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/")) {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello World!", response.content)
            }
        }
    }

    //@Test
    //fun `show users api`() {
    //}

    @Test
    fun `show users page`() {
        dbSetup(dest) {
            deleteAllFrom(listOf("users"))
            insertUsersFixture(UsersFixture().active().copy(id = 1, name = "テスト1"))
            insertUsersFixture(UsersFixture().stopped().copy(id = 2, name = "テスト2"))
        }.launch()

        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/users")) {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    //@Test
    //fun `create and show user page`() {
    //}
}
