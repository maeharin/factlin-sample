package factlin_sample.junit4

import org.junit.Test
import java.time.LocalDateTime


data class Item(val id: Int, val name: String)

class AssertionSample {
    private val now = LocalDateTime.now()
    private val item = Item(1, "foo")
    private val item2 = Item(1, "foo")

    private var counter = 0

    @Test
    fun `assersions`() {
        println("----------")
        println(counter)
        println("----------")
        counter++

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
    fun `kotlin-test assersions`() {
        println("----------")
        println(counter)
        println("----------")
        counter++

        kotlin.test.assertEquals(expected = 1, actual = 1, message = "メッセージ")
        kotlin.test.assertNotEquals(illegal = 0, actual = 1, message = "メッセージ")
        kotlin.test.assertNotNull(actual = 0)
    }
}