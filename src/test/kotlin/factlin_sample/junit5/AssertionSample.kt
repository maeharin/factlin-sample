package factlin_sample.junit5

import org.junit.jupiter.api.Test

class AssertionSample {
    @Test
    fun `junit4 assersions`() {
        org.junit.Assert.assertEquals(1, 1)
    }

    @Test
    fun `jupiter assertions`() {
        org.junit.jupiter.api.Assertions.assertEquals(0, 1) { "メッセージ" }
        org.junit.jupiter.api.assertAll("assert all",
                { org.junit.jupiter.api.Assertions.assertEquals(1, 1) },
                { org.junit.jupiter.api.Assertions.assertEquals(2, 2) },
                { org.junit.jupiter.api.Assertions.assertEquals(3, 3) }
        )
    }
}