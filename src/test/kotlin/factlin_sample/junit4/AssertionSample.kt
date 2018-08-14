package factlin_sample.junit4

import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.hasSize
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.Test
import java.time.LocalDateTime


data class Item(val id: Int, val name: String)

class AssertionSample {
    private val now = LocalDateTime.now()
    private val item = Item(1, "foo")
    private val item2 = Item(1, "foo")

    @Test
    fun `junitのassersions`() {
        org.junit.Assert.assertEquals(0, 0)
        org.junit.Assert.assertNotEquals(1, 0)
        org.junit.Assert.assertEquals("メッセージ", 0, 0)
        org.junit.Assert.assertEquals("foo", "foo")
        org.junit.Assert.assertEquals(now, now)
        org.junit.Assert.assertEquals(item, item2) // 違うインスタンスだがequalsはtrue
        org.junit.Assert.assertSame(item, item) // 同じインスタンスであること
        org.junit.Assert.assertNotSame(item, item2) // 違うインスタンスであること

        org.junit.Assert.assertTrue(true)
        org.junit.Assert.assertFalse(false)

        org.junit.Assert.assertNull(null)
        org.junit.Assert.assertNotNull(0)
    }

    @Test
    fun `junitに組み込まれているhamcrest core matchers`() {
        org.junit.Assert.assertThat(0, Is(0))
        org.junit.Assert.assertThat(0, Is(not(1)))
        org.junit.Assert.assertThat("foo", Is("foo"))
        org.junit.Assert.assertThat(null, Is(nullValue()))
        org.junit.Assert.assertThat(item, Is(item))
        org.junit.Assert.assertThat(item, Is(sameInstance(item)))
        org.junit.Assert.assertThat(item, Is(item2))
        org.junit.Assert.assertThat(item, Is(not(sameInstance(item2))))
        org.junit.Assert.assertThat("foo", Is(startsWith("f")))
        org.junit.Assert.assertThat("foo", Is(containsString("oo")))
        org.junit.Assert.assertThat(listOf(1,2,3), Is(hasItem(2)))
        org.junit.Assert.assertThat(listOf(1,2,3), Is(hasItems(2,3)))
    }

    @Test
    fun `hamcrest-libraryのmatchers`() {
        org.junit.Assert.assertThat(5, Is(greaterThan(4)))
        org.junit.Assert.assertThat(emptyList<String>(), Is(empty()))
        org.junit.Assert.assertThat(listOf(1,2,3), hasSize(3))
    }

    @Test
    fun `kotlin-testのassersions`() {
        kotlin.test.assertEquals(expected = 1, actual = 1, message = "メッセージ")
        kotlin.test.assertNotEquals(illegal = 0, actual = 1, message = "メッセージ")
        kotlin.test.assertNotNull(actual = 0)
    }
}