package factlin_sample.junit4

import java.time.LocalDateTime

data class Item(val id: Int, val name: String)

class AssertionSample {
    private val now = LocalDateTime.now()
    private val item = Item(1, "foo")
    private val item2 = Item(1, "foo")

    // インスタンスのデフォルトのライフサイクルの違い
    // junit4: メソッド単位
    // testng: クラス単位
    private var counter = 0

    @org.testng.annotations.Test
    fun `junit4 assersions`() {
        println("----------")
        println(counter)
        println("----------")
        counter++

        org.testng.Assert.assertEquals(0, 0)
        org.testng.Assert.assertNotEquals(1, 0)
        org.testng.Assert.assertEquals("foo", "foo")
        org.testng.Assert.assertEquals(now, now)
        org.testng.Assert.assertEquals(item, item2) // 違うインスタンスだがequalsはtrue
        org.testng.Assert.assertSame(item, item) // 同じインスタンスであること
        org.testng.Assert.assertNotSame(item, item2) // 違うインスタンスであること

        org.testng.Assert.assertTrue(true)
        org.testng.Assert.assertFalse(false)

        org.testng.Assert.assertNull(null)
        org.testng.Assert.assertNotNull(0)

    }

    @org.testng.annotations.Test
    fun `kotlin-test-junit assersions`() {
        println("----------")
        println(counter)
        println("----------")
        counter++

        kotlin.test.assertEquals(expected = 1, actual = 1, message = "メッセージ")
        kotlin.test.assertNotEquals(illegal = 0, actual = 1, message = "メッセージ")
        kotlin.test.assertNotNull(actual = 0)
    }
}