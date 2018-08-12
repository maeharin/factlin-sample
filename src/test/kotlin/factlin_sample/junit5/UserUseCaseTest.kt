package factlin_sample.junit5

import com.maeharin.factlin.fixtures.UsersFixture
import com.maeharin.factlin.fixtures.insertUsersFixture
import com.maeharin.factlin.fixtures_ext.active
import com.maeharin.factlin.fixtures_ext.stopped
import com.maeharin.factlin_sample.usecase.showUsers
import com.maeharin.factlin_sample.domain.UserJobType
import com.maeharin.factlin_sample.usecase.CreateOrUpdateUserInput
import com.maeharin.factlin_sample.usecase.createUser
import com.maeharin.factlin_sample.usecase.showUserById
import com.ninja_squad.dbsetup.destination.DriverManagerDestination
import com.ninja_squad.dbsetup_kotlin.dbSetup
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.LocalDate

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
    fun `show users`() {
        dbSetup(dest) {
            deleteAllFrom(listOf("users"))
            insertUsersFixture(UsersFixture().active().copy(id = 1, name = "テスト1"))
            insertUsersFixture(UsersFixture().stopped().copy(id = 2, name = "テスト2"))
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

    @Test
    fun `show user by id`() {
        dbSetup(dest) {
            deleteAllFrom(listOf("users"))
            insertUsersFixture(UsersFixture().active().copy(id = 1, name = "テスト1"))
        }.launch()

        val user = showUserById(1)

        assertAll("user 1",
                { assertEquals(1, user.id) },
                { assertEquals("テスト1", user.name) },
                { assertEquals(UserJobType.ENGINEER, user.job) }
        )
    }

    @Test
    fun `create user` () {
        val input = CreateOrUpdateUserInput(
                name = "前原 秀徳",
                job = UserJobType.TEACHER,
                age = 0,
                birthDay = LocalDate.parse("1982-10-07"),
                nickName = "maeharin"
        )
        val id = createUser(input)
        val user = showUserById(id)

        assertAll("created user",
                { assertEquals(id, user.id) },
                { assertEquals("前原 秀徳", user.name) },
                { assertEquals(UserJobType.TEACHER, user.job) },
                { assertEquals( 0, user.age) },
                { assertEquals(LocalDate.parse("1982-10-07"), user.birthDay) },
                { assertEquals("maeharin", user.nickName) }
        )
    }
}
