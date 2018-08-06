package factlin_sample

import com.maeharin.factlin.fixtures.UsersFixture
import com.maeharin.factlin.fixtures.insertUsersFixture
import com.maeharin.factlin.fixtures_ext.active
import com.maeharin.factlin.fixtures_ext.inActive
import com.maeharin.factlin_sample.application.usecase.showUsers
import com.maeharin.factlin_sample.domain.UserJobType
import com.ninja_squad.dbsetup.destination.DriverManagerDestination
import com.ninja_squad.dbsetup_kotlin.dbSetup
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class UserUseCaseTest {
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
    fun test_showUsers() {
        dbSetup(dest) {
            deleteAllFrom(listOf("users"))
            insertUsersFixture(UsersFixture().active().copy(id = 1, name = "テスト1"))
            insertUsersFixture(UsersFixture().inActive().copy(id = 2, name = "テスト2"))
        }.launch()

        val users = showUsers()

        users[0].also { user ->
            assertAll("first user",
                    { assertEquals(1, user.id)},
                    { assertEquals("テスト1", user.name)},
                    { assertEquals(UserJobType.ENGINEER, user.job)}
            )
        }

        users[1].also { user ->
            assertAll("second user",
                    { assertEquals(2, user.id)},
                    { assertEquals("テスト2", user.name)},
                    { assertEquals(UserJobType.ENGINEER, user.job)}
            )
        }
    }
}
