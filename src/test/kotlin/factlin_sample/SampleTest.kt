package factlin_sample

import com.maeharin.factlin.fixtures.UsersFixture
import com.maeharin.factlin.fixtures.insertUsersFixture
import com.maeharin.factlin.fixtures_ext.active
import com.maeharin.factlin.fixtures_ext.inActive
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
            insertUsersFixture(UsersFixture().active().copy(id = 1, name = "テスト1"))
            insertUsersFixture(UsersFixture().inActive().copy(id = 2, name = "テスト2"))
        }.launch()

        val stmt = dest.connection.createStatement()
        val rs = stmt.executeQuery("select * from users order by id asc")

        assertTrue(rs.next())
        assertEquals("1", rs.getString("id"))
        assertEquals("テスト1", rs.getString("name"))
        assertEquals("ACTIVE", rs.getString("status"))
        assertEquals("エンジニア", rs.getString("job"))
        assertEquals("30", rs.getString("age"))

        assertTrue(rs.next())
        assertEquals("2", rs.getString("id"))
        assertEquals("テスト2", rs.getString("name"))
        assertEquals("IN_ACTIVE", rs.getString("status"))
        assertEquals("エンジニア", rs.getString("job"))
        assertEquals("30", rs.getString("age"))
    }
}
