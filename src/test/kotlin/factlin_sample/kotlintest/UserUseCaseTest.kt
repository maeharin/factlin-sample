package factlin_sample.kotlintest

import com.maeharin.factlin.fixtures.UsersFixture
import com.maeharin.factlin.fixtures.insertUsersFixture
import com.maeharin.factlin.fixtures_ext.active
import com.maeharin.factlin.fixtures_ext.stopped
import com.maeharin.factlin_sample.usecase.CreateOrUpdateUserInput
import com.maeharin.factlin_sample.usecase.createUser
import com.maeharin.factlin_sample.usecase.showUsers
import com.maeharin.factlin_sample.domain.UserJobType
import com.maeharin.factlin_sample.usecase.showUserById
import com.ninja_squad.dbsetup.destination.DriverManagerDestination
import com.ninja_squad.dbsetup_kotlin.dbSetup
import io.kotlintest.*
import io.kotlintest.specs.StringSpec
import org.jetbrains.exposed.sql.Database
import java.time.LocalDate

class UserUseCaseTest: StringSpec() {
    val dbUrl = "jdbc:postgresql://${System.getenv("DB_HOST")}/dvdrental"
    val dbUser = "postgres"
    val dbPassword = ""
    val dbDriver = "org.postgresql.Driver"

    val dest = DriverManagerDestination(dbUrl, dbUser, dbPassword)

    override fun beforeTest(description: Description) {
        dbSetup(dest) {
            deleteAllFrom(listOf("users"))
        }.launch()
    }

    init {
        Class.forName(dbDriver)
        Database.connect(url = dbUrl, driver = dbDriver, user = dbUser, password = dbPassword)

        "showUsers" {
            dbSetup(dest) {
                insertUsersFixture(UsersFixture().active().copy(id = 1, name = "テスト1"))
                insertUsersFixture(UsersFixture().stopped().copy(id = 2, name = "テスト2"))
            }.launch()

            val users = showUsers()

            "first user" should {
                val user = users[0]
                assertSoftly {
                    user.id shouldBe 1
                    user.name shouldBe "テスト1"
                    user.job shouldBe UserJobType.ENGINEER
                }
            }

            "second user" should {
                val user = users[1]
                assertSoftly {
                    user.id shouldBe 2
                    user.name shouldBe "テスト2"
                    user.job shouldBe UserJobType.ENGINEER
                }
            }
        }

        "show user by id" {
            dbSetup(dest) {
                insertUsersFixture(UsersFixture().active().copy(id = 1, name = "テスト1"))
            }.launch()

            val user = showUserById(1)

            assertSoftly {
                user.id shouldBe 1
                user.name shouldBe "テスト1"
                user.job shouldBe UserJobType.ENGINEER
            }
        }

        "create user" {
            val input = CreateOrUpdateUserInput(
                    name = "前原 秀徳",
                    job = UserJobType.TEACHER,
                    age = 0,
                    birthDay = LocalDate.parse("1982-10-07"),
                    nickName = "maeharin"
            )
            val id = createUser(input)
            val user = showUserById(id)

            assertSoftly {
                user.id shouldBe id
                user.name shouldBe "前原 秀徳"
                user.job shouldBe UserJobType.TEACHER
                user.age shouldBe 0
                user.birthDay shouldBe LocalDate.parse("1982-10-07")
                user.nickName shouldBe "maeharin"
            }
        }

        "update user" {

        }

        "delete user" {

        }
    }
}
