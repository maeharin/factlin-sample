package factlin_sample

import com.maeharin.factlin.fixtures.UsersFixture
import com.maeharin.factlin.fixtures.insertUsersFixture
import com.ninja_squad.dbsetup.destination.DriverManagerDestination
import com.ninja_squad.dbsetup_kotlin.dbSetup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SampleTest {
    val dest = DriverManagerDestination("jdbc:postgresql://192.168.99.100/dvdrental", "postgres", "")

    init {
        Class.forName("org.postgresql.Driver")
    }

    @Test
    fun testInsertUser() {
        dbSetup(dest) {
            deleteAllFrom(listOf("users"))
            insertUsersFixture(UsersFixture())
        }.launch()

        val stmt = dest.connection.createStatement()
        val rs = stmt.executeQuery("select * from users")

        assertTrue(rs.next())
        assertEquals("", rs.getString("name"))
    }
}
