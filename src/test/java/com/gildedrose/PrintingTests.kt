package com.gildedrose

import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class PrintingTests {
    private val now = LocalDate.parse("2025-08-10")

    @Test
    fun `print stock list`() {
        val stock = listOf<Item>()
        val expected = listOf("10 August 2025")

        assertEquals(expected, stock.printout(now))
    }

    @Test
    fun `print non empty stock list`() {
       val stock = listOf(
           Item("banana", now.minusDays(1), 42),
           Item("kumquat", now.plusDays(1), 101)
       )

        val expected = listOf(
            "10 August 2025",
            "banana, -1, 42",
            "kumquat, 1, 101"
        )

        assertEquals(expected, stock.printout(now))
    }
}


