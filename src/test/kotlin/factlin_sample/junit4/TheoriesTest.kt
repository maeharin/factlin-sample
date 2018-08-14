package factlin_sample.junit4

import org.junit.Assert.assertThat
import org.junit.experimental.theories.DataPoints
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.experimental.theories.Theories
import org.junit.experimental.theories.Theory
import org.junit.runner.RunWith


@RunWith(Theories::class)
class TheoriesTest {
    data class Data(val a: Int, val b: Int, val expected: Int)

    companion object {
        @DataPoints
        @JvmField
        val values = listOf(
                Data(1,2,3),
                Data(1,-2,-1),
                Data(1,-1,0)
        )
    }

    @Theory
    fun `test add`(d: Data) {
        assertThat(d.a + d.b, Is(d.expected))
    }
}